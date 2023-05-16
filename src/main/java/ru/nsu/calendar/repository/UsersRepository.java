package ru.nsu.calendar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.calendar.entities.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

}