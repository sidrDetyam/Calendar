package ru.nsu.calendar.task.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDto {
    private Long id;
    private Long userId;
    private String taskName;
    private String description;
    private LocalDate taskDate;
    private Short countOfRepeat;
    private Boolean isFinish;
}
