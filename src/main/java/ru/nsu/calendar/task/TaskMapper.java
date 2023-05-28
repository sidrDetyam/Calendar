package ru.nsu.calendar.task;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nsu.calendar.entities.Task;
import ru.nsu.calendar.task.dto.TaskDto;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "userId",
            expression = "java(task.getUserId().getId())")
    TaskDto toDto(Task task);
}
