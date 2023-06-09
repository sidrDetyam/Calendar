package ru.nsu.calendar.event;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.calendar.dto.DateDto;
import ru.nsu.calendar.entities.Event;
import ru.nsu.calendar.entities.User;
import ru.nsu.calendar.event.dto.EventDto;
import ru.nsu.calendar.repository.EventRepository;
import ru.nsu.calendar.security.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;


    public void createEvent(EventDto eventDto) {
        User user = userService.getCurrentUser();
        eventRepository.save(eventDto.toEvent(user));
    }

    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public void updateEvent(EventDto eventDto) {
        if(eventDto.id() == null) {
            throw new NoSuchElementException();
        }

        Event event = eventRepository.findById(eventDto.id()).orElseThrow();
        eventDto.updateEvent(event);
        eventRepository.save(event);
    }

    public List<EventDto> getEventByDateDto(DateDto dateDto) {
        LocalDate date = dateDto.toLocalDate();
        return getEventDtoByLocalDate(date);
    }

    public List<EventDto> getEventByCurrentDate() {
        LocalDate date = LocalDate.now();
        return getEventDtoByLocalDate(date);
    }

    private List<EventDto> getEventDtoByLocalDate(LocalDate date) {
        return getEventByLocalDate(date).stream()
                .map(EventDto::from)
                .collect(Collectors.toList());
    }

    private List<Event> getEventByLocalDate(LocalDate localDate) {
        return eventRepository.findAllByEventDateAndUser(localDate, userService.getCurrentUser());
    }
}
