package ru.nsu.calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.nsu.calendar.security.dto.CredentialsDto;
import ru.nsu.calendar.security.dto.JwtLoginResponseDto;
import ru.nsu.calendar.security.dto.JwtRefreshRequestDto;
import ru.nsu.calendar.utils.IntegrationTest;

@IntegrationTest
public class AuthTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test() {
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

        assert tokens != null;
        JwtRefreshRequestDto refreshRequestDto = JwtRefreshRequestDto.builder()
                .refreshToken(tokens.getRefreshToken())
                .build();

        webTestClient.post()
                .uri("/v1/auth/refresh")
                .body(Mono.just(refreshRequestDto), JwtRefreshRequestDto.class)
                .exchange()
                .expectStatus().isOk();
    }
}
