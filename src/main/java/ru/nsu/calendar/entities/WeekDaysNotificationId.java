package ru.nsu.calendar.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class WeekDaysNotificationId implements Serializable {

    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "week_day_id")
    private Long weekDayId;
}
