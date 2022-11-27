package ru.practicum.ewm.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.repo.RequestRepository;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final RequestRepository requestRepository;

    public Request validationRequestOrThrow(long id) {
        return requestRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Request id = %x not found.", id), "Model not found."));
    }
}
