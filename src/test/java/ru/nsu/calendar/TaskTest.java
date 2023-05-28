package ru.nsu.calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.nsu.calendar.security.dto.CredentialsDto;
import ru.nsu.calendar.security.dto.JwtLoginResponseDto;
import ru.nsu.calendar.task.dto.CreateTaskRequestDto;
import ru.nsu.calendar.task.dto.TaskDto;
import ru.nsu.calendar.utils.IntegrationTest;
import ru.nsu.calendar.utils.testcontainers.TestAuthUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@IntegrationTest
public class TaskTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void basicCrud() {
        final JwtLoginResponseDto tokens = TestAuthUtils
                .signUpAndLogin(webTestClient, new CredentialsDto("u", "p"));

        final CreateTaskRequestDto requestDto = CreateTaskRequestDto
                .builder()
                .taskName("name")
                .countOfRepeat((short) 4)
                .description("descr")
                .taskDateTime(LocalDateTime.now())
                .build();

        webTestClient.post()
                .uri("/v1/tasks")
                .headers(httpHeaders -> {
                    assert tokens != null;
                    httpHeaders.setBearerAuth(tokens.getAccessToken());
                })
                .body(Mono.just(requestDto), CreateTaskRequestDto.class)
                .exchange()
                .expectStatus().isOk();

        final AtomicLong id = new AtomicLong();
        webTestClient.get()
                .uri("/v1/tasks?taskName=name&description=des")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.getAccessToken()))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskDto.class)
                .consumeWith(listEntityExchangeResult -> {
                    Assertions.assertEquals(Objects
                            .requireNonNull(listEntityExchangeResult.getResponseBody()).size(), 1);
                    id.set(listEntityExchangeResult.getResponseBody().get(0).getId());
                });

        webTestClient.delete()
                .uri("/v1/tasks/" + id.get())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.getAccessToken()))
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri("/v1/tasks")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokens.getAccessToken()))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskDto.class)
                .consumeWith(listEntityExchangeResult -> Assertions.assertEquals(Objects
                        .requireNonNull(listEntityExchangeResult.getResponseBody()).size(), 0));
    }
}
