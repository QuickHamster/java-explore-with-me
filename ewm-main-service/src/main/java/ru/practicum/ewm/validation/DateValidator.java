package ru.practicum.ewm.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.util.Const;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Component
@RequiredArgsConstructor
public class DateValidator {

    public void validateDateBeforeOrThrow(LocalDateTime dateEvent, int hours) {
        if (dateEvent.isBefore(LocalDateTime.now().plusHours(hours))) {
            throw new ValidationException("Event time must be later then " + hours + " hours.", "Not valid date.");
        }
    }

    public LocalDateTime validateFormatDateOrThrow(String strDateTime) {
        try {
            return LocalDateTime.parse(strDateTime, Const.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new ValidationException("Incorrect datetime format: '" + strDateTime + "'. Expected format: " +
                    Const.DATE_TIME_FORMAT, "Not valid date.");
        }
    }

    public void validateDateAfterNowOrThrow(LocalDateTime localDateTime) {
        if (!localDateTime.isAfter(LocalDateTime.now())) {
            throw new ValidationException("Time must be later then now.", "Not valid date.");
        }
    }
}
