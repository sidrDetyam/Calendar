package ru.nsu.calendar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.calendar.entities.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

}
