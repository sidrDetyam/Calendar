package ru.nsu.calendar.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.nsu.calendar.utils.NullFieldException;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Log4j2
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(value = {NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noSuchElement(Exception ignore) {
        return "Resource not found";
    }

    @ExceptionHandler(value = {NullFieldException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String nullField(NullFieldException ex) {
        log.warn("Bad request: " + ex.getMessage());
        return ex.getMessage();
    }
}
