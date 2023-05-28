package ru.nsu.calendar.utils.testcontainers;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.nsu.calendar.security.dto.CredentialsDto;
import ru.nsu.calendar.security.dto.JwtLoginResponseDto;

@UtilityClass
public class TestAuthUtils {

    public static JwtLoginResponseDto signUpAndLogin(final @NonNull WebTestClient webTestClient,
                                                     final @NonNull CredentialsDto credentialsDto){
        webTestClient.post()
                .uri("/v1/auth/sign-up")
                .body(Mono.just(credentialsDto), CredentialsDto.class)
                .exchange()
                .expectStatus().isOk();

        return webTestClient.post()
                .uri("/v1/auth/login")
                .body(Mono.just(credentialsDto), CredentialsDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(JwtLoginResponseDto.class)
                .returnResult().getResponseBody();
    }
}
