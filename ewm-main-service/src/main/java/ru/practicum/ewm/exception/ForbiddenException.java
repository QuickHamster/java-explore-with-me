package ru.practicum.ewm.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    private final String reason;

    public ForbiddenException(String message, String reason) {
        super(message);
        this.reason = reason;
    }
}
