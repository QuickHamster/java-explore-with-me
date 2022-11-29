package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.client.StatMapper;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.model.dto.*;
import ru.practicum.ewm.event.repo.EventRepository;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.util.Const;
import ru.practicum.ewm.validation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService  {

    private final EventRepository eventRepository;
    private final UserValidator userValidator;
    private final CategoryValidator categoryValidator;
    private final DateValidator dateValidator;
    private final EventValidator eventValidator;
    private final EventStateValidator eventStateValidator;
    private final StatsClient statsClient;

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto eventDto) {
        log.info("Trying to add event: userId {}, eventDto {}.", userId, eventDto);
        dateValidator.validateDateBeforeOrThrow(eventDto.getEventDate(), Const.NUMBER_HOUR_BEFORE_START_FOR_USER);
        User user = userValidator.validationUserOrThrow(userId);
        Category category = categoryValidator.validationCategoryOrThrow(eventDto.getCategory());
        Event event = EventMapper.toEvent(user, category, eventDto);
        event = eventRepository.save(event);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event, statsClient);
        log.info("Event added successfully: {}.", eventFullDto);
        return eventFullDto;
    }

    @Override
    public EventFullDto updateByAdmin(Long eventId, AdminUpdateEventRequest eventDto) {
        log.info("Trying update event by admin: eventId {}, eventDto{}.", eventId, eventDto);
        Event event = eventValidator.validationEventOrThrow(eventId);
        event = patchAndSaveEventByAdmin(event, eventDto);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event, statsClient);
        log.info("Event update successfully by admin: {}.", eventFullDto);
        return eventFullDto;
    }

    @Override
    public EventFullDto updateByInitiator(Long userId, UpdateEventRequest eventDto) {
        log.info("Trying update event by initiator: userId {}, eventDto{}.", userId, eventDto);
        userValidator.validationUserOrThrow(userId);
        Event event = eventRepository.getByInitiator(userId);
        eventStateValidator.validateEventStatePublishedOrThrow(event);
        if (event.getState().equals(EventState.CANCELED)) {
            event.setState(EventState.PENDING);
        }
        event = patchAndSaveEventByInitiator(event, eventDto);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event, statsClient);
        log.info("Event update successfully by initiator: {}.", eventFullDto);
        return eventFullDto;
    }



    @Override
    public EventFullDto publishEvent(Long eventId) {
        log.info("Trying publish event {}.", eventId);
        Event event = eventValidator.validationEventOrThrow(eventId);
        dateValidator.validateDateBeforeOrThrow(event.getEventDate(), Const.NUMBER_HOUR_BEFORE_START_FOR_ADMIN);
        event.setState(EventState.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        event = eventRepository.save(event);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event, statsClient);
        log.info("Event publish successfully: {}.", eventFullDto);
        return eventFullDto;
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        log.info("Trying reject event {}.", eventId);
        Event event = eventValidator.validationEventOrThrow(eventId);
        eventStateValidator.validateEventStateNotPendingOrThrow(event);
        event.setState(EventState.CANCELED);
        event = eventRepository.save(event);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event, statsClient);
        log.info("Event reject successfully: {}.", eventFullDto);
        return eventFullDto;
    }

    @Override
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        log.info("Trying cancel event {} by user {}.", eventId, userId);
        Event event = eventValidator.validationEventOrThrow(eventId);
        eventStateValidator.validateEventStateNotPendingOrThrow(event);
        eventValidator.validateThatUserIdIsInitiatorEventOrThrow(event, userId);
        event.setState(EventState.CANCELED);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(eventRepository.save(event), statsClient);
        log.info("Event cancel successfully: {}.", eventFullDto);
        return eventFullDto;
    }

    @Override
    public EventFullDto findByInitiator(Long userId, Long eventId) {
        log.info("Trying find event {} by initiator {}.", eventId, userId);
        userValidator.validationUserOrThrow(userId);
        Event event = eventValidator.validationEventOrThrow(eventId);
        eventValidator.validateThatUserIdIsInitiatorEventOrThrow(event, userId);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event, statsClient);
        log.info("Find event successfully: {}.", eventFullDto);
        return eventFullDto;
    }

    @Override
    public EventFullDto getEventByPublic(Long eventId, HttpServletRequest request) {
        log.info("Trying to get event {}.", eventId);
        statsClient.postStats(StatMapper.toEndpointHitDto("ewm-main-server", request));
        Event event = eventValidator.validationEventOrThrow(eventId);
        eventStateValidator.validateEventStateNotPublishedOrThrow(event);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event, statsClient);
        log.info("Get event successfully: {}.", eventFullDto);
        return eventFullDto;
    }

    @Override

    public List<EventFullDto> getAllAtFilterByAdmin(List<Long> users, List<String> states, List<Long> categories,
                                                    String rangeStart, String rangeEnd, Integer from, Integer size) {
        log.info("Trying to get all events by admin: users {}, states {}, categories {}, rangeStart {}, rangeEnd {}, " +
                        "from {}, size {}.",
                users, states, categories, rangeStart, rangeEnd, from, size);
        List<Long> userIds = userValidator.validationUsers(users);
        List<Long> categoryIds = categoryValidator.validationCategories(categories);
        List<EventState> eventStates = (states == null)
            ? List.of(EventState.PUBLISHED, EventState.CANCELED, EventState.PENDING)
            : eventStateValidator.validationStatesOrThrow(states);
        LocalDateTime dateStart = parseDateOrNow(rangeStart);
        LocalDateTime dateEnd = parseDateOrNow(rangeEnd);
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = eventRepository
                .findAllByUsersAndCategoriesAndStates(userIds, categoryIds, eventStates, dateStart, dateEnd, pageable);
        List<EventFullDto> eventFullDtoList = EventMapper.toFullDtoList(events, statsClient);
        log.info("{} found by the specified parameters for admin.", eventFullDtoList.size());
        return eventFullDtoList;
    }



    @Override
    public List<EventFullDto> getAllByInitiator(Long userId, Pageable page) {
        log.info("Trying to get all by initiator: userId {}, page {}.", userId, page);
        userValidator.validationUserOrThrow(userId);
        List<EventFullDto> eventFullDtoList = EventMapper.toFullDtoList(
                eventRepository.findAllByInitiatorId(userId, page), statsClient);
        log.info("{} events get by initiator.", eventFullDtoList.size());
        return eventFullDtoList;
    }

    @Override
    public List<EventShortDto> getAllByPublic(String text, Long[] categories, Boolean paid, String rangeStart,
                                              String rangeEnd, Boolean onlyAvailable, String sortType,
                                              Integer from, Integer size, HttpServletRequest request) {
        log.info("Trying to get all events by parameters: text {}, categories {}, paid {}, rangeStart {}, rangeEnd {}" +
                        "onlyAvailable {},  sortType {}, from {}, size {}, request {}.",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable,  sortType, from, size, request);
        statsClient.postStats(StatMapper.toEndpointHitDto("ewm-main-server", request));
        List<Long> categoryIds = categoryValidator.validationCategories(List.of(categories));
        LocalDateTime dateStart = parseDateOrNow(rangeStart);
        LocalDateTime dateEnd = parseDateOrNow(rangeEnd);
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findAllEventsByParameters(
                EventState.PUBLISHED, categoryIds, text, paid, dateStart, dateEnd, pageable);
        List<EventShortDto> eventShortDtoList = doSortEvents(events, sortType, onlyAvailable);
        log.info("{} found by the specified parameters for public.", eventShortDtoList.size());
        return eventShortDtoList;
    }

    private LocalDateTime parseDateOrNow(String strDateTime) {
        return (strDateTime != null)
                ? LocalDateTime.parse(strDateTime, Const.DATE_TIME_FORMATTER)
                : LocalDateTime.now();
    }

    private Comparator<Event> eventComparator(String sort) {
        switch (sort) {
            case ("VIEWS"):
                return Comparator.comparing(Event::getViews);
            case ("EVENT_DATE"):
                return Comparator.comparing(Event::getEventDate);
            default:
                throw new ValidationException("Unknown sort parameter: " + sort + ".", "Not valid parameter.");
        }
    }

    private List<EventShortDto> doSortEvents(List<Event> events, String sort, Boolean onlyAvailable) {
        return  (onlyAvailable)
            ? events.stream()
                    .filter(event -> event.getParticipantLimit() > event.getConfirmedRequests())
                    .sorted(eventComparator(sort))
                    .map((Event event) -> EventMapper.toEventShortDto(event, statsClient))
                    .collect(Collectors.toList())
            : events.stream()
                .sorted(eventComparator(sort))
                .map((Event event) -> EventMapper.toEventShortDto(event, statsClient))
                .collect(Collectors.toList());
    }

    private Event patchAndSaveEventByAdmin(Event event, AdminUpdateEventRequest eventDto) {
        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getCategory() != null) {
            event.setCategory(categoryValidator.validationCategoryOrThrow(eventDto.getCategory()));
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getEventDate() != null) {
            dateValidator.validateDateBeforeOrThrow(eventDto.getEventDate(), Const.NUMBER_HOUR_BEFORE_START_FOR_USER);
            event.setEventDate(eventDto.getEventDate());
        }
        if (eventDto.getLocation() != null) {
            event.getLocation().setLat(eventDto.getLocation().getLat());
            event.getLocation().setLon(eventDto.getLocation().getLon());
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getRequestModeration() != null) {
            event.setRequestModeration(eventDto.getRequestModeration());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        return eventRepository.save(event);
    }

    private Event patchAndSaveEventByInitiator(Event event, UpdateEventRequest eventDto) {
        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getCategory() != null) {
            event.setCategory(categoryValidator.validationCategoryOrThrow(eventDto.getCategory()));
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getEventDate() != null) {
            dateValidator.validateDateBeforeOrThrow(eventDto.getEventDate(), Const.NUMBER_HOUR_BEFORE_START_FOR_USER);
            event.setEventDate(eventDto.getEventDate());
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        return eventRepository.save(event);
    }
}
