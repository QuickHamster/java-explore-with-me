package ru.practicum.ewm.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.event.model.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    EventFullDto addEvent(Long userId, NewEventDto eventDto);

    EventFullDto updateByAdmin(Long eventId, AdminUpdateEventRequest eventDto);

    EventFullDto updateByInitiator(Long userId, UpdateEventRequest eventDto);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    EventFullDto cancelEvent(Long userId, Long eventId);

    EventFullDto findByInitiator(Long userId, Long eventId);

    EventFullDto getEventByPublic(Long eventId);

    List<EventFullDto> getAllAtFilterByAdmin(List<Long> users, List<String> states, List<Long> categories,
                                             String rangeStart, String rangeEnd, Integer from, Integer size);

    List<EventFullDto> getAllByInitiator(Long userId, Pageable page);

    List<EventShortDto> getAllByPublic(String text,
                                          Long[] categories,
                                          Boolean paid,
                                          String rangeStart,
                                          String rangeEnd,
                                          Boolean onlyAvailable,
                                          String sortType,
                                          Integer from,
                                          Integer size,
                                          HttpServletRequest request);
}
