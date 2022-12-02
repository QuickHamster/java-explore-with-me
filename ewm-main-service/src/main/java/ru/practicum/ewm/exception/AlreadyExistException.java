package ru.practicum.ewm.exception;

import lombok.Getter;

@Getter
public class AlreadyExistException extends RuntimeException {
    private final String reason;

    public AlreadyExistException(String message, String reason) {
        super(message);
        this.reason = reason;
    }
}
