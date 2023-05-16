package ru.nsu.calendar.security.jwt;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import ru.nsu.calendar.security.Role;

import java.util.Set;

@RequiredArgsConstructor
public class JwtAuthentication implements Authentication {
    private boolean authenticated;
    private final @NonNull String username;
    private final @NonNull Set<? extends Role> roles;

    @Override
    public Set<? extends Role> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return username;
    }

}