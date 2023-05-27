package ru.nsu.calendar.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.calendar.security.dto.CredentialsDto;
import ru.nsu.calendar.security.dto.JwtLoginResponseDto;
import ru.nsu.calendar.security.dto.JwtRefreshRequestDto;
import ru.nsu.calendar.security.dto.JwtRefreshResponseDto;


@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<JwtLoginResponseDto> login(@RequestBody final CredentialsDto authRequest) {
        final JwtLoginResponseDto response = authService.login(authRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtRefreshResponseDto> getNewAccessToken(@RequestBody final JwtRefreshRequestDto request) {
        final JwtRefreshResponseDto response = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("sign-up")
    public void signUp(@RequestBody final CredentialsDto singUpRequest) {
        authService.singUp(singUpRequest);
    }
}
