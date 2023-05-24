package ru.nsu.calendar.security.dto;

import lombok.NonNull;
import lombok.Value;

@Value
public class JwtLoginResponseDto {
    static String type = "Bearer";
    @NonNull String accessToken;
    @NonNull String refreshToken;
}
