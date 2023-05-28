package ru.nsu.calendar.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.nsu.calendar.utils.NonNullField;

import java.time.LocalDate;

@Value
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditTaskRequestDto {
    @NonNullField Long id;
    @NonNullField String taskName;
    @NonNullField String description;
    @NonNullField Short countOfRepeat;
    @NonNullField Boolean isFinish;
}
