package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.util.Const;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventControllerPublic {
    private final EventService eventService;
    private final StatsClient statsClient;

    @GetMapping()
    public List<EventShortDto> getAllByPublic(
            @RequestParam(value = "text", required = false, defaultValue = "") String text,
            @RequestParam(value = "categories", required = false, defaultValue = "List.of()") Long[] categories,
            @RequestParam(value = "paid", required = false, defaultValue = "false") Boolean paid,
            @RequestParam(value = "rangeStart", required = false) String rangeStart,
            @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
            @RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(value = "sort", required = false, defaultValue = "EVENT_DATE") String sort,
            @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size", required = false, defaultValue = Const.SIZE_OF_PAGE)
            @PositiveOrZero Integer size,
            HttpServletRequest request) {
        log.info("Get all events at params: ip {}, text {}, categories {}, paid {}, rangeStart {}, rangeEnd {}, " +
                "onlyAvailable {}, sort {}, from {}, size {}.", request.getRemoteAddr(), text, categories, paid,
                rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return eventService.getAllByPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from,
                size, request);
    }


    @GetMapping("/{eventId}")
    public EventFullDto getEventByPublic(@PathVariable(value = "eventId") @Positive Long eventId,
                                              HttpServletRequest request) {
        log.info("Get event: ip: {}, path {}, eventId {}.", request.getRemoteAddr(), request.getRequestURI(), eventId);
        return eventService.getEventByPublic(eventId, request);
    }
}
