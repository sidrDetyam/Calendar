package ru.nsu.calendar.security.dto;

import lombok.NonNull;
import lombok.Value;

@Value
public class JwtRefreshResponseDto {
    @NonNull String accessToken;
}
