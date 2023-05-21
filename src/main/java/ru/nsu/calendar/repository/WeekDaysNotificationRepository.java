package ru.nsu.calendar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.calendar.entities.WeekDaysNotification;
import ru.nsu.calendar.entities.WeekDaysNotificationId;

@Repository
public interface WeekDaysNotificationRepository extends CrudRepository<WeekDaysNotification, WeekDaysNotificationId> {
}
