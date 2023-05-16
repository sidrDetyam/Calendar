package ru.nsu.calendar.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "week_days_notification")
public class WeekDaysNotification {
    @EmbeddedId
    private WeekDaysNotificationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_WeekDaysNotification_Notification"), insertable = false, updatable = false)
    private Notification notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_day_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_WeekDaysNotification_WeekDay"), insertable = false, updatable = false)
    private WeekDay weekDay;
}
