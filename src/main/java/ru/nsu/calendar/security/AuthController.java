package ru.nsu.calendar.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.calendar.security.dto.JwtLoginRequestDto;
import ru.nsu.calendar.security.dto.JwtLoginResponseDto;
import ru.nsu.calendar.security.dto.JwtRefreshRequestDto;
import ru.nsu.calendar.security.dto.JwtRefreshResponseDto;


@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<JwtLoginResponseDto> login(@RequestBody final JwtLoginRequestDto authRequest) throws Exception {
        final JwtLoginResponseDto response = authService.login(authRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtRefreshResponseDto> getNewAccessToken(@RequestBody final JwtRefreshRequestDto request) throws Exception {
        final JwtRefreshResponseDto response = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
