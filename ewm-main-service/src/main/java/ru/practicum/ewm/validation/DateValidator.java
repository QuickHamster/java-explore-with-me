package ru.practicum.ewm.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.exception.ValidationException;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DateValidator {

    public void validateDateBeforeOrThrow(LocalDateTime dateEvent, int hours) {
        if (dateEvent.isBefore(LocalDateTime.now().plusHours(hours))) {
            throw new ValidationException("Event time must be later then " + hours + " hours.", "Not valid date.");
        }
    }
}
