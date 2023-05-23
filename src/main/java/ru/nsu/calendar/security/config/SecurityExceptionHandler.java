package ru.nsu.calendar.security.config;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.nsu.calendar.security.exceptions.LoginException;
import ru.nsu.calendar.security.exceptions.RefreshException;

@RestControllerAdvice
public class SecurityExceptionHandler {
    @ExceptionHandler(value = {LoginException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @NonNull String loginFail(@NonNull Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {RefreshException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @NonNull String refreshFail(@NonNull Exception ex) {
        return ex.getMessage();
    }
}