package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repo.EventRepository;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.model.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.model.dto.RequestMapper;
import ru.practicum.ewm.request.repo.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.validation.EventStateValidator;
import ru.practicum.ewm.validation.EventValidator;
import ru.practicum.ewm.validation.RequestValidator;
import ru.practicum.ewm.validation.UserValidator;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserValidator userValidator;
    private final EventRepository eventRepository;
    private final EventValidator eventValidator;
    private final EventStateValidator eventStateValidator;
    private final RequestValidator requestValidator;

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        log.info("Trying get requests: {}.", userId);
        userValidator.validationUserOrThrow(userId);
        List<Request> requestList = requestRepository.getAllByRequesterId(userId);
        List<ParticipationRequestDto> participationRequestDtos = RequestMapper.toListRequestDto(requestList);
        log.info("Requests get successfully: {}.", participationRequestDtos);
        return participationRequestDtos;
    }

    @Override
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        log.info("Trying to add a requests: userId {}, eventId {}.", userId, eventId);
        Event event = eventValidator.validationEventOrThrow(eventId);
        eventValidator.validateInitiatorCantSendRequestHimselfOrThrow(event, userId);
        requestValidator.validateExistsByRequesterIdAndEventIdAndStatusOrThrow(event.getId(), userId,
                RequestStatus.CONFIRMED);
        eventStateValidator.validateThatEventStateNotPublishedOrThrow(event);
        eventValidator.validateParticipantLimitNotReachedOrThrow(event);
        User user = userValidator.validationUserOrThrow(userId);
        Request request = new Request();
        request.setEvent(event);
        request.setRequester(user);
        request.setStatus(
                event.getRequestModeration()
                ? RequestStatus.PENDING
                : RequestStatus.CONFIRMED);
        request = requestRepository.save(request);
        ParticipationRequestDto participationRequestDto = RequestMapper.toRequestDto(request);
        if (participationRequestDto.getStatus().equals(RequestStatus.CONFIRMED)) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }
        log.info("Requests added successfully: {}.", request);
        return participationRequestDto;
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId) {
        log.info("Trying to confirm a requests: userId {}, eventId {}, requestId {}.", userId, eventId, requestId);
        Event event = eventValidator.validationEventOrThrow(eventId);
        Request request = requestValidator.validationRequestOrThrow(requestId);
        eventValidator.validateThatUserIdIsInitiatorEventOrThrow(event, userId);

        request.setStatus(
                ((event.getParticipantLimit() > event.getConfirmedRequests())
                        || event.getParticipantLimit() == 0
                        || !event.getRequestModeration())
                ? RequestStatus.CONFIRMED
                : RequestStatus.REJECTED);

        requestRepository.save(request);

        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }

        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            List<Request> requests = requestRepository.getAllByEventAndStatus(event, RequestStatus.CONFIRMED);
            for (Request r : requests) {
                r.setStatus(RequestStatus.REJECTED);
                requestRepository.save(r);
            }
        }
        ParticipationRequestDto participationRequestDto = RequestMapper.toRequestDto(request);
        log.info("Request confirm successfully: {}.", participationRequestDto);
        return participationRequestDto;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        log.info("Trying to cancel a requests: userId {}, requestId {}.", userId, requestId);
        Request request = requestValidator.validationRequestOrThrow(requestId);
        userValidator.validationUserOrThrow(userId);
        requestValidator.validateUserIsOwnerRequestOrThrow(request, userId);
        request.setStatus(RequestStatus.CANCELED);
        ParticipationRequestDto requestDto = RequestMapper.toRequestDto(requestRepository.save(request));
        log.info("Request  cancel successfully: {}.", requestDto);
        return requestDto;
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId) {
        log.info("Trying to reject a requests: userId {}, eventId {}, requestId {}.", userId, eventId, requestId);
        Event event = eventValidator.validationEventOrThrow(eventId);
        eventValidator.validateThatUserIdIsInitiatorEventOrThrow(event, userId);
        Request request = requestValidator.validationRequestOrThrow(requestId);
        request.setStatus(RequestStatus.REJECTED);
        request = requestRepository.save(request);
        ParticipationRequestDto participationRequestDto = RequestMapper.toRequestDto(request);
        log.info("Request reject successfully: {}.", participationRequestDto);
        return participationRequestDto;
    }

    @Override
    public List<ParticipationRequestDto> findOwnerEventRequests(Long userId, Long eventId) {
        log.info("Trying to find owner event requests: userId {}, eventId {}.", userId, eventId);
        userValidator.validationUserOrThrow(userId);
        eventValidator.validationEventOrThrow(eventId);
        List<ParticipationRequestDto> participationRequestDtos =
                RequestMapper.toListRequestDto(requestRepository.findOwnerEventRequests(userId, eventId));
        log.info("Find owner event requests successfully: {}.", participationRequestDtos);
        return participationRequestDtos;
    }
}
