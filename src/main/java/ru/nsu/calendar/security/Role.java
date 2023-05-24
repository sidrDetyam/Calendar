package ru.nsu.calendar.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    USER("User");

    private final @NonNull String roleName;

    @Override
    public @NonNull String getAuthority() {
        return roleName;
    }
}
