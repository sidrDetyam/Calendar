package ru.nsu.calendar.dto;

import java.sql.Time;
import java.time.LocalTime;

public record TimeDto(int seconds, int minutes, int hours) {
    public static TimeDto from(LocalTime localTime){
        return new TimeDto(localTime.getSecond(), localTime.getMinute(), localTime.getHour());
    }

    public LocalTime toLocalTime() {
        return LocalTime.of(hours(), minutes(), seconds());
    }
}
