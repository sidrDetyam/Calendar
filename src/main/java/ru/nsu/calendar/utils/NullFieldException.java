package ru.nsu.calendar.utils;

import lombok.NonNull;

public class NullFieldException extends IllegalArgumentException{
    public NullFieldException(@NonNull String cause){
        super(cause);
    }
}
