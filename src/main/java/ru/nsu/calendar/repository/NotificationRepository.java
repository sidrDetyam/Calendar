package ru.nsu.calendar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.calendar.entities.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

}