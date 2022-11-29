package ru.practicum.ewm.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repo.RequestRepository;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final RequestRepository requestRepository;

    public Request validationRequestOrThrow(long id) {
        return requestRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Request id = %x not found.", id), "Model not found."));
    }

    public void validateExistsByRequesterIdAndEventIdAndStatusOrThrow(Long eventId, Long userId, RequestStatus status) {
        if (requestRepository.existsByRequesterIdAndEventIdAndStatus(eventId, userId, status)) {
            throw new ValidationException("Request already exist.","Validation error.");
        }
    }

    public void validateUserIsOwnerRequestOrThrow(Request request, Long userId) {
        if (!request.getRequester().getId().equals(userId)) {
            throw new ForbiddenException("User" + userId + "isn't owner of the request " +
                    request.getRequester().getId() + ".", "Forbidden operation.");
        }
    }
}
