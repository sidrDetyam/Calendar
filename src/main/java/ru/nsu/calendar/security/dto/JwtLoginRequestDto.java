package ru.nsu.calendar.security.dto;

import lombok.NonNull;
import lombok.Value;

@Value
public class JwtLoginRequestDto {
    @NonNull String username;
    @NonNull String password;
}