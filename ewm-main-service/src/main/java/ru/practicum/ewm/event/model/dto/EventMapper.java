package ru.practicum.ewm.event.model.dto;

import ru.practicum.ewm.Const;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.dto.CategoryMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.location.model.dto.LocationMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.dto.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventMapper {

    /*public static Event toEvent(User user, Category category, Location location, EventInputDto eventInputDto) {
        if (eventInputDto.getState() == null) {
            eventInputDto.setState(State.PENDING.toString());
        }
        return Event.builder()
                .annotation(eventInputDto.getAnnotation())
                .category(category)
                .createdOn(LocalDateTime.now())
                .description(eventInputDto.getDescription())
                .eventDate(LocalDateTime.parse(eventInputDto.getEventDate(), FORMATTER))
                .initiator(user)
                .confirmedRequests(0)
                .location(location)
                .paid(eventInputDto.getPaid())
                .participantLimit(eventInputDto.getParticipantLimit())
                .requestModeration(eventInputDto.getRequestModeration())
                .title(eventInputDto.getTitle())
                .state(State.valueOf(eventInputDto.getState()))
                .views(0)
                .build();
    }
*/
    public static EventFullDto toEventFullDto(Event event, EventClient eventClient) {
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
                .views(eventClient.getViews(event.getId()))
                .build();
        if (event.getPublishedOn() != null) {
            eventFullDto.setPublishedOn(event.getPublishedOn().format(Const.DATE_TIME_FORMATTER));
        }
        return eventFullDto;
    }

    public static EventShortDto toEventShortDto(Event event, EventClient eventClient) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.fromCategoryToCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(Const.DATE_TIME_FORMATTER))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(eventClient.getViews(event.getId()))
                .build();
    }

    public static List<EventFullDto> toFullDtoList(List<Event> userEvents, EventClient eventClient) {
        return userEvents.stream().map((Event event) -> toEventFullDto(event, eventClient)).collect(Collectors.toList());
    }

    public static List<EventShortDto> toShortDtoList(Set<Event> events, EventClient eventClient) {
        return events.stream().map((Event event) -> toEventShortDto(event, eventClient)).collect(Collectors.toList());
    }
}
