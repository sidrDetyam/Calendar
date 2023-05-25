package ru.nsu.calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.nsu.calendar.dto.DateDto;
import ru.nsu.calendar.dto.TimeDto;
import ru.nsu.calendar.event.dto.EventDto;
import ru.nsu.calendar.utils.IntegrationTest;

@IntegrationTest
public class EventTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void test_on_create_event() {
        EventDto eventDto = new EventDto(null,
                "ev",
                "descr",
                new DateDto(1, 1, 2024),
                new TimeDto(1, 1, 1),
                1,
                true);

        webTestClient.post()
                .uri("/v1/event")
                .body(Mono.just(eventDto), EventDto.class)
                .exchange()
                .expectStatus().isOk();
    }
}
