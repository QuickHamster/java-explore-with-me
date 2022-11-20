package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.model.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getRequests(Long userId);

    ParticipationRequestDto addRequest(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId);

    List<ParticipationRequestDto> findOwnerEventRequests(Long userId, Long eventId);
}
