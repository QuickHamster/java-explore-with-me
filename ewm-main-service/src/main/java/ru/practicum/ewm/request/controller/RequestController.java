package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.model.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.RequestService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ParticipationRequestDto addRequest(
            @PathVariable(value = "userId") @PositiveOrZero Long userId, @RequestParam @PositiveOrZero Long eventId) {
        log.info("Add request: userId {}, eventId {}.", userId, eventId);
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(
            @PathVariable(value = "userId") @PositiveOrZero Long userId,
            @PathVariable(value = "requestId") @PositiveOrZero Long requestId) {
        log.info("Cancel request: userId {}, eventId {}.", userId, requestId);
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping
    public List<ParticipationRequestDto> getRequests(
            @PathVariable(value = "userId") @PositiveOrZero Long userId) {
        log.info("Get user requests: userId {}.", userId);
        return requestService.getRequests(userId);
    }
}
