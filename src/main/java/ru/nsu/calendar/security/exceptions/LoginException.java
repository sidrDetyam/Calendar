package ru.nsu.calendar.security.exceptions;

import lombok.NonNull;

public class LoginException extends RuntimeException{
    public LoginException(@NonNull String message){
        super(message);
    }
}
