package ru.nsu.calendar.event.dto;

import ru.nsu.calendar.dto.DateDto;
import ru.nsu.calendar.dto.TimeDto;
import ru.nsu.calendar.entities.Event;
import ru.nsu.calendar.entities.User;

public record EventDto(Long id,
                       String eventName,
                       String description,
                       DateDto dateDto,
                       TimeDto timeDto,
                       Integer countOfRepeat,
                       Boolean isFinish) {

    public static EventDto from(Event event) {
        return new EventDto(
                event.getId(),
                event.getEventName(),
                event.getDescription(),
                DateDto.from(event.getEventDate()),
                TimeDto.from(event.getEventTime()),
                event.getCountOfRepeat(),
                event.getIsFinish()
        );
    }

    public Event toEvent(User user) {
        return Event.builder()
                .id(id)
                .eventDate(dateDto.toLocalDate())
                .eventTime(timeDto.toLocalTime())
                .user(user)
                .description(description)
                .eventName(eventName)
                .isFinish(isFinish)
                .countOfRepeat(countOfRepeat)
                .build();
    }
}
