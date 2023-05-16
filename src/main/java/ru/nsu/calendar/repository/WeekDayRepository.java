package ru.nsu.calendar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.calendar.entities.WeekDay;

@Repository
public interface WeekDayRepository extends CrudRepository<WeekDay, Long> {

}