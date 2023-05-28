package ru.nsu.calendar.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import java.time.LocalDateTime;

import ru.nsu.calendar.utils.NonNullField;

@Value
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTaskRequestDto {
    @NonNullField String taskName;
    @NonNullField String description;
    @NonNullField LocalDateTime taskDateTime;
    @NonNullField Short countOfRepeat;
}
