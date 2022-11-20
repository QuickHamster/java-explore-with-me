package ru.practicum.ewm.exception;

public class AlreadyExistException extends RuntimeException {
    private final String reason;

    public AlreadyExistException(String message, String reason) {
        super(message);
        this.reason = reason;
    }
}
