package ru.nsu.calendar.security;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.calendar.entities.JwtToken;
import ru.nsu.calendar.entities.Users;
import ru.nsu.calendar.repository.UsersRepository;
import ru.nsu.calendar.security.dto.JwtLoginRequestDto;
import ru.nsu.calendar.security.dto.JwtLoginResponseDto;
import ru.nsu.calendar.security.dto.JwtRefreshResponseDto;
import ru.nsu.calendar.security.jwt.JwtProvider;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsersRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public @NonNull JwtLoginResponseDto login(@NonNull final JwtLoginRequestDto authRequest) throws Exception {
        final Users user = userRepository.getByUsername(authRequest.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!user.getPassword().equals(authRequest.getPassword())) {
            throw new Exception("Incorrect password");
        }

        final Set<Role> userRoles = Set.of(Role.USER);
        final JwtLoginResponseDto jwtLoginResponseDto = generateJwtResponse(user, userRoles);
        final JwtToken jwtToken = JwtToken.builder()
                .refreshToken(jwtLoginResponseDto.getRefreshToken())
                .user(user)
                .build();
        user.getTokens().add(jwtToken);
        userRepository.save(user);
        return jwtLoginResponseDto;
    }

    @Transactional(readOnly = true)
    public @NonNull JwtRefreshResponseDto refreshAccessToken(@NonNull final String refreshToken) throws Exception {
        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw new Exception("Invalid refresh token");
        }

        final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        final String username = claims.getSubject();

        final Users user = userRepository.getByUsername(username)
                .orElseThrow(() -> new Exception("User not found: %s".formatted(username)));

        final boolean isJwtTokenExists = user.getTokens().stream()
                .map(JwtToken::getRefreshToken)
                .anyMatch(refreshToken::equals);

        if (isJwtTokenExists) {
            throw new Exception("New and old refresh tokens aren`t equal");
        }

        final Set<Role> userRoles = Set.of(Role.USER);
        final String accessToken = jwtProvider.generateAccessToken(user.getUsername(), userRoles);
        return new JwtRefreshResponseDto(accessToken);
    }

    private @NonNull JwtLoginResponseDto generateJwtResponse(@NonNull final Users user,
                                                             @NonNull final Set<? extends Role> roles) {
        final String accessToken = jwtProvider.generateAccessToken(user.getUsername(), roles);
        final String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());
        return new JwtLoginResponseDto(accessToken, refreshToken);
    }
}
