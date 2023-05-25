package ru.nsu.calendar.dto;

import java.time.LocalDate;

public record DateDto(int day, int month, int year) {

    public LocalDate toLocalDate() {
        return LocalDate.of(year(), month(), day());
    }

    public static DateDto from(LocalDate localDate) {
        return new DateDto(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }
}
