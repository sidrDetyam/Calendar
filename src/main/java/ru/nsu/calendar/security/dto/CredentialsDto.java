package ru.nsu.calendar.security.dto;

import lombok.NonNull;
import lombok.Value;

public record CredentialsDto(
    @NonNull String username,
    @NonNull String password
){}