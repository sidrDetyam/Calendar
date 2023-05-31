package ru.nsu.calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.nsu.calendar.dto.DateDto;
import ru.nsu.calendar.dto.TimeDto;
import ru.nsu.calendar.event.dto.EventDto;
import ru.nsu.calendar.security.dto.CredentialsDto;
import ru.nsu.calendar.security.dto.JwtLoginResponseDto;
import ru.nsu.calendar.utils.IntegrationTest;

@IntegrationTest
public class EventTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void test_on_create_event() {

        CredentialsDto credentialsDto = new CredentialsDto("user", "password");

        webTestClient.post()
                .uri("/v1/auth/sign-up")
                .body(Mono.just(credentialsDto), CredentialsDto.class)
                .exchange()
                .expectStatus().isOk();

        JwtLoginResponseDto tokens = webTestClient.post()
                .uri("/v1/auth/login")
                .body(Mono.just(credentialsDto), CredentialsDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(JwtLoginResponseDto.class)
                .returnResult().getResponseBody();

        DateDto eventDate =  new DateDto(1, 1, 2024);

        EventDto eventDto = new EventDto(null,
                "ev",
                "descr",
                eventDate,
                new TimeDto(1, 1, 1),
                true);

        EventDto updateEventDto = new EventDto(1L,
                "ev",
                "description",
                eventDate,
                new TimeDto(1, 1, 1),
                true);

        webTestClient.post()
                .uri("/v1/event")
                .headers(httpHeaders -> {
                    assert tokens != null;
                    httpHeaders.setBearerAuth(tokens.getAccessToken());
                })
                .body(Mono.just(eventDto), EventDto.class)
                .exchange()
                .expectStatus().isOk();

        webTestClient.post()
                .uri("/v1/event/date")
                .headers(httpHeaders -> {
                    assert tokens != null;
                    httpHeaders.setBearerAuth(tokens.getAccessToken());
                }).body(Mono.just(eventDate), DateDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$[0].eventName").isEqualTo("ev");

        webTestClient.put()
                .uri("/v1/event")
                .headers(httpHeaders -> {
                    assert tokens != null;
                    httpHeaders.setBearerAuth(tokens.getAccessToken());
                })
                .body(Mono.just(updateEventDto), EventDto.class)
                .exchange()
                .expectStatus().isOk();

        webTestClient.post()
                .uri("/v1/event/date")
                .headers(httpHeaders -> {
                    assert tokens != null;
                    httpHeaders.setBearerAuth(tokens.getAccessToken());
                }).body(Mono.just(eventDate), DateDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$[0].description").isEqualTo("description");
    }
}
