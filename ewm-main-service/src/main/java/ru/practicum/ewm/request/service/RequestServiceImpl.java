package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.request.model.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.repo.RequestRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService{

    private final RequestRepository requestRepository;
    //private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        log.info("Trying get requests: {}.", userId);
        log.info("Requests get successfully: {}.", userId);

        return null;
    }

    @Override
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        log.info("Trying to add a requests: userId {}, eventId {}.", userId, eventId);
        log.info("Requests added successfully: {}.", userId);
        return null;
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId) {
        log.info("Trying to confirm a requests: userId {}, eventId {}, requestId {}.", userId, eventId, requestId);
        log.info("Requests confirm successfully: {}.", userId);
        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        log.info("Trying to cancel a requests: userId {}, requestId {}.", userId, requestId);
        return null;
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId) {
        log.info("Trying to reject a requests: userId {}, eventId {}, requestId {}.", userId, eventId, requestId);
        return null;
    }

    @Override
    public List<ParticipationRequestDto> findOwnerEventRequests(Long userId, Long eventId) {
        log.info("Trying to find owner event requests: userId {}, eventId {}.", userId, eventId);
        return null;
    }
}
