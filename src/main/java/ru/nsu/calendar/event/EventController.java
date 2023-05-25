package ru.nsu.calendar.event;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.calendar.event.dto.EventDto;

@RestController
@RequestMapping("/v1/event")
@AllArgsConstructor
public class EventController {

    private EventService eventService;

    @PostMapping
    public void createEvent(@RequestBody EventDto eventDto) {
        eventService.createEvent(eventDto);
    }

}
