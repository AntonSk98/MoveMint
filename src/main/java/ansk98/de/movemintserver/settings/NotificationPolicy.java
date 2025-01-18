package ansk98.de.movemintserver.settings;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Entity that defines a notification policy within which time range during the day a user is allowed to be notified.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Entity
public class NotificationPolicy {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false, name = "notify_from")
    private LocalTime from;

    @Column(nullable = false, name = "notify_to")
    private LocalTime to;

    protected NotificationPolicy() {

    }

    private NotificationPolicy(DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
        this.id = UUID.randomUUID();
        this.dayOfWeek = dayOfWeek;
        this.from = from;
        this.to = to;
    }

    public static NotificationPolicy of(DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
        return new NotificationPolicy(dayOfWeek, from, to);
    }

    public UUID getId() {
        return id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getFrom() {
        return from;
    }

    public LocalTime getTo() {
        return to;
    }
}
