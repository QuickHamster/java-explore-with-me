package ru.practicum.ewm.event.model.dto;

import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.dto.CategoryMapper;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.dto.UserMapper;
import ru.practicum.ewm.util.Const;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventMapper {

    public static Event toEvent(User user, Category category, NewEventDto eventDto) {
        return Event.builder()
                .annotation(eventDto.getAnnotation())
                .category(category)
                .createdOn(LocalDateTime.now())
                .description(eventDto.getDescription())
                .eventDate(eventDto.getEventDate())
                .initiator(user)
                .confirmedRequests(0)
                .location(eventDto.getLocation())
                .paid(eventDto.getPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .requestModeration(eventDto.getRequestModeration())
                .title(eventDto.getTitle())
                .state(EventState.valueOf(EventState.PENDING.toString()))
                .views(0)
                .build();
    }

    public static EventFullDto toEventFullDto(Event event, StatsClient statsClient) {
        EventFullDto eventFullDto = EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.fromCategoryToCategoryDto(event.getCategory()))
                .createdOn(event.getCreatedOn().format(Const.DATE_TIME_FORMATTER))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(Const.DATE_TIME_FORMATTER))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .confirmedRequests(event.getConfirmedRequests())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(statsClient.getViews(event.getId()))
                .build();
        if (event.getPublishedOn() != null) {
            eventFullDto.setPublishedOn(event.getPublishedOn().format(Const.DATE_TIME_FORMATTER));
        }
        return eventFullDto;
    }

    public static EventShortDto toEventShortDto(Event event, StatsClient statsClient) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.fromCategoryToCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(Const.DATE_TIME_FORMATTER))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(statsClient.getViews(event.getId()))
                .build();
    }

    public static List<EventFullDto> toFullDtoList(List<Event> userEvents, StatsClient statsClient) {
        return userEvents.stream().map((Event event) -> toEventFullDto(event, statsClient))
                .collect(Collectors.toList());
    }

    public static List<EventShortDto> toShortDtoList(Set<Event> events, StatsClient statsClient) {
        for (Event e: events) {
            toEventShortDto(e, statsClient);
        }
        return events.stream().map((Event event) -> toEventShortDto(event, statsClient)).collect(Collectors.toList());
    }
}
