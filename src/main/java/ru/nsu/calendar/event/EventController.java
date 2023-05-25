package ru.nsu.calendar.event;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nsu.calendar.dto.DateDto;
import ru.nsu.calendar.event.dto.EventDto;

import java.util.List;

@RestController
@RequestMapping("/v1/event")
@AllArgsConstructor
public class EventController {

    private EventService eventService;

    @PostMapping
    public void createEvent(@RequestBody EventDto eventDto) {
        eventService.createEvent(eventDto);
    }

    @GetMapping("/current")
    public List<EventDto> getEventByCurrentDate() {
        return eventService.getEventByCurrentDate();
    }

    @PostMapping("/date")
    public List<EventDto> getEventByCurrentDate(@RequestBody DateDto dateDto) {
        return eventService.getEventByDateDto(dateDto);
    }
}
