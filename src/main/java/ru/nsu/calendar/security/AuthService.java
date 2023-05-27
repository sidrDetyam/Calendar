package ru.nsu.calendar.security;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.calendar.entities.JwtToken;
import ru.nsu.calendar.entities.User;
import ru.nsu.calendar.repository.UserRepository;
import ru.nsu.calendar.security.dto.CredentialsDto;
import ru.nsu.calendar.security.dto.JwtLoginResponseDto;
import ru.nsu.calendar.security.dto.JwtRefreshResponseDto;
import ru.nsu.calendar.security.exceptions.LoginException;
import ru.nsu.calendar.security.exceptions.RefreshException;
import ru.nsu.calendar.security.exceptions.SignUpException;
import ru.nsu.calendar.security.jwt.JwtProvider;

import java.util.Set;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AuthService implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public @NonNull JwtLoginResponseDto login(@NonNull final CredentialsDto authRequest) {
        final Supplier<LoginException> loginExceptionSupplier = () -> new LoginException("Incorrect login or password");

        final User user = userRepository.getByUsername(authRequest.username())
                .orElseThrow(loginExceptionSupplier);

        if (!passwordEncoder.matches(authRequest.password(), user.getPassword())) {
            throw loginExceptionSupplier.get();
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
    public @NonNull JwtRefreshResponseDto refreshAccessToken(@NonNull final String refreshToken) {
        final Supplier<RefreshException> refreshExceptionSupplier = () -> new RefreshException("Refresh failed");

        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw refreshExceptionSupplier.get();
        }

        final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        final String username = claims.getSubject();

        final User user = userRepository.getByUsername(username)
                .orElseThrow(refreshExceptionSupplier);

        final boolean isJwtTokenExists = user.getTokens().stream()
                .map(JwtToken::getRefreshToken)
                .anyMatch(refreshToken::equals);

        if (!isJwtTokenExists) {
            throw refreshExceptionSupplier.get();
        }

        final Set<Role> userRoles = Set.of(Role.USER);
        final String accessToken = jwtProvider.generateAccessToken(user.getUsername(), userRoles);
        return new JwtRefreshResponseDto(accessToken);
    }

    private @NonNull JwtLoginResponseDto generateJwtResponse(@NonNull final User user,
                                                             @NonNull final Set<? extends Role> roles) {
        final String accessToken = jwtProvider.generateAccessToken(user.getUsername(), roles);
        final String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());
        return new JwtLoginResponseDto(accessToken, refreshToken);
    }

    @Override
    public User getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + "not found"));
    }

    public void singUp(CredentialsDto credentials) {
        if(userRepository.getByUsername(credentials.username()).isPresent()) {
            throw new SignUpException(String.format("User with username %s already exists", credentials.username()));
        }

        User user = createUser(credentials);
        userRepository.save(user);
    }

    @NotNull
    private User createUser(CredentialsDto credentials) {
        User user = new User();
        user.setUsername(credentials.username());
        user.setPassword(passwordEncoder.encode(credentials.password()));
        return user;
    }


}
