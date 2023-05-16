package ru.nsu.calendar.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.calendar.entities.WeekDaysNotification;
import ru.nsu.calendar.entities.WeekDaysNotificationId;

public interface WeekDaysNotificationRepository extends CrudRepository<WeekDaysNotification, WeekDaysNotificationId> {
}
