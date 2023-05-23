package ru.nsu.calendar.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nsu.calendar.security.Role;

import javax.crypto.SecretKey;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Slf4j
@Component
public class JwtProvider {

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;
    private final Integer jwtAccessExpirationMinutes;
    private final Integer jwtRefreshExpirationMinutes;

    public JwtProvider(
            @Value("${jwt.secret.access}") @NonNull String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") @NonNull String jwtRefreshSecret,
            @Value("${jwt.access-expiration-seconds}") @NonNull Integer jwtAccessExpiration,
            @Value("${jwt.refresh-expiration-days}") @NonNull Integer jwtRefreshExpiration
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.jwtAccessExpirationMinutes = jwtAccessExpiration;
        this.jwtRefreshExpirationMinutes = jwtRefreshExpiration;
    }

    public @NonNull String generateAccessToken(@NonNull final String username,
                                               @NonNull final Set<? extends Role> roles) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(JwtUtils.dateFromNow(jwtAccessExpirationMinutes, ChronoUnit.SECONDS))
                .signWith(jwtAccessSecret)
                .claim("roles", roles)
                .compact();
    }

    public @NonNull String generateRefreshToken(@NonNull final String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(JwtUtils.dateFromNow(jwtRefreshExpirationMinutes, ChronoUnit.DAYS))
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean validateAccessToken(@NonNull final String accessToken) {
        return JwtUtils.validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull final String refreshToken) {
        return JwtUtils.validateToken(refreshToken, jwtRefreshSecret);
    }

    public Claims getAccessClaims(@NonNull final String token) {
        return JwtUtils.getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull final String token) {
        return JwtUtils.getClaims(token, jwtRefreshSecret);
    }
}