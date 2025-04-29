package ansk98.de.movemintserver.settings;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Entity that defines a notification policy within which time range during the day a user is allowed to be notified.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Embeddable
public class NotificationPolicy {

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false, name = "notify_from")
    private LocalTime from;

    @Column(nullable = false, name = "notify_to")
    private LocalTime to;

    protected NotificationPolicy() {

    }

    private NotificationPolicy(DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
        this.dayOfWeek = dayOfWeek;
        this.from = from;
        this.to = to;
    }

    public static NotificationPolicy of(DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
        return new NotificationPolicy(dayOfWeek, from, to);
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
