package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        log.warn(ex.getMessage());
        return ErrorResponse.builder()
                .errors(List.of())
                .message(ex.getLocalizedMessage())
                .reason(ex.getReason())
                .status(HttpStatus.NOT_FOUND.toString())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExistException(AlreadyExistException ex) {
        log.warn(ex.getMessage());
        return ErrorResponse.builder()
                .errors(List.of())
                .message(ex.getLocalizedMessage())
                .reason(ex.getReason())
                .status(HttpStatus.CONFLICT.toString())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(value = {
            ValidationException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(Throwable ex) {
        log.warn(ex.getMessage());
        return ErrorResponse.builder()
                .errors(List.of())
                .message(ex.getLocalizedMessage())
                .reason(ex.getClass().toString())
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now())
                .build();
    }


    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(ForbiddenException ex) {
        log.warn(ex.getMessage());
        return ErrorResponse.builder()
                .errors(List.of())
                .message(ex.getLocalizedMessage())
                .reason(ex.getReason())
                .status(String.valueOf(HttpStatus.FORBIDDEN))
                .timestamp(LocalDateTime.now())
                .build();
    }
}
