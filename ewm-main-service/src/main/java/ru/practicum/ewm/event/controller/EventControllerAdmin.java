package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.service.EventService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventControllerAdmin {
    private final EventService eventService;

    @PutMapping("/{eventId}")
    public EventFullDto updateByAdmin(@PathVariable(value = "eventId") @PositiveOrZero Long eventId,
                                             @RequestBody AdminUpdateEventRequest eventDto) {
        log.info("Update event by admin: eventId {}, eventDto {}.", eventId, eventDto);
        return eventService.updateByAdmin(eventId, eventDto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable(value = "eventId") @PositiveOrZero Long eventId) {
        log.info("Publish event: eventId {}.", eventId);
        return eventService.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable(value = "eventId") @PositiveOrZero Long eventId) {
        log.info("Reject event: eventId {}.", eventId);
        return eventService.rejectEvent(eventId);
    }

    @GetMapping()
    public List<EventFullDto> getAllAtFilterByAdmin(
            @RequestParam(value = "users", defaultValue = "") List<Long> userIds,
            @RequestParam(value = "states", required = false) List<String> states,
            @RequestParam(value = "categories", required = false) List<Long> categoryIds,
            @RequestParam(value = "rangeStart", required = false) String rangeStart,
            @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
            @RequestParam(value = "from",  defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size",  defaultValue = "10") @PositiveOrZero Integer size) {
        return eventService.getAllAtFilterByAdmin(userIds, states, categoryIds, rangeStart, rangeEnd, from, size);
    }
}
