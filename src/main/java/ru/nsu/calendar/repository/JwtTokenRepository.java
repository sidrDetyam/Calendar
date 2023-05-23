package ru.nsu.calendar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.calendar.entities.JwtToken;
import ru.nsu.calendar.entities.Users;

import java.util.Set;

@Repository
public interface JwtTokenRepository extends CrudRepository<JwtToken, Long> {
    Set<JwtToken> findByUser(Users user);
}
