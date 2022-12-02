package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.NewEventDto;
import ru.practicum.ewm.event.model.dto.UpdateEventRequest;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.model.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.RequestService;
import ru.practicum.ewm.util.Const;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventControllerPrivate {
    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping
    public EventFullDto addEvent(@PathVariable(value = "userId") Long userId,
                                 @Valid @RequestBody NewEventDto eventDto) {
        log.info("Add event: userId {}, eventDto {}.", userId, eventDto);
        return eventService.addEvent(userId, eventDto);
    }

    @PatchMapping
    public EventFullDto updateByInitiator(@PathVariable(value = "userId") @PositiveOrZero Long userId,
                                                 @RequestBody @Valid UpdateEventRequest updateEventRequestDto) {
        log.info("Update event by initiator: userId {}, updateEventRequestDto {}.", userId, updateEventRequestDto);
        return eventService.updateByInitiator(userId, updateEventRequestDto);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable(value = "userId") @PositiveOrZero Long userId,
                                    @PathVariable(value = "eventId") @PositiveOrZero Long eventId) {
        log.info("Cancel event: userId {}, eventId {}.", userId, eventId);
        return eventService.cancelEvent(userId, eventId);
    }

    @GetMapping("/{eventId}")
    public EventFullDto findByInitiator(@PathVariable(value = "userId") @PositiveOrZero Long userId,
                                              @PathVariable(value = "eventId") @PositiveOrZero Long eventId) {
        log.info("Find event by initiator: userId {}, eventId {}.", userId, eventId);
        return eventService.findByInitiator(userId, eventId);
    }

    @GetMapping(("/{eventId}/requests"))
    public List<ParticipationRequestDto> findOwnerEventRequests(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "eventId") @PositiveOrZero Long eventId)  {
        log.info("Find owner event requests: userId {}, eventId {}.", userId, eventId);
        return requestService.findOwnerEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable(value = "userId") @PositiveOrZero Long userId,
                                                  @PathVariable(value = "eventId") @PositiveOrZero Long eventId,
                                                  @PathVariable(value = "reqId") @PositiveOrZero Long reqId) {
        log.info("Confirm request: userId {}, eventId {}, reqId {}.", userId, eventId, reqId);
        return requestService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable(value = "userId") @PositiveOrZero Long userId,
                                                 @PathVariable(value = "eventId") @PositiveOrZero Long eventId,
                                                 @PathVariable(value = "reqId") @PositiveOrZero Long reqId) {
        log.info("Reject request: userId {}, eventId {}, reqId {}.", userId, eventId, reqId);
        return requestService.rejectRequest(userId, eventId, reqId);
    }

    @GetMapping
    public List<EventFullDto> getAllByInitiator(
            @PathVariable(value = "userId") @PositiveOrZero Long userId,
            @RequestParam(value = "from",
                    required = false,
                    defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = Const.SIZE_OF_PAGE) @PositiveOrZero Integer size) {
        log.info("Get all events by initiator: userId {}, from {}, size {}.", userId, from, size);
        return eventService.getAllByInitiator(userId, PageRequest.of(from / size, size));
    }
}
