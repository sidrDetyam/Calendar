package ru.nsu.calendar.security.exceptions;

public class SignUpException extends RuntimeException{
    public SignUpException(String message) {
        super(message);
    }
}
