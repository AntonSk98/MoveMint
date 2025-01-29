package ansk98.de.movemintserver.settings;

import ansk98.de.movemintserver.activities.common.ActivityType;
import ansk98.de.movemintserver.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Entity that contains settings of a user.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Entity
public class Settings {

    @Id
    private UUID id;

    @Embedded
    private ActivitySettings activitySettings;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "settings_id", nullable = false)
    private List<NotificationPolicy> notificationSettings;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    protected Settings() {

    }

    private Settings(User user, ActivitySettings activitySettings, List<NotificationPolicy> notificationSettings) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.activitySettings = activitySettings;
        this.notificationSettings = notificationSettings;
    }

    public static Settings create(User user, ActivitySettings.Builder activitySettings, List<NotificationPolicy> notificationSettings) {
        return new Settings(user, activitySettings.build(), notificationSettings);
    }

    public void update(ActivitySettings.Builder activitySettings, List<NotificationPolicy> notificationSettings) {
        this.activitySettings = activitySettings.build();
        this.notificationSettings = notificationSettings;
    }

    public UUID getId() {
        return id;
    }

    public ActivitySettings getActivitySettings() {
        return activitySettings;
    }

    public List<NotificationPolicy> getNotificationSettings() {
        return Collections.unmodifiableList(notificationSettings);
    }

    public User getUser() {
        return user;
    }

    public boolean isNotificationEnabledFor(ActivityType activityType) {
        return switch (activityType) {
            case STRETCHING_ACTIVITY -> activitySettings.isEnabledStretchingNotification();
            case VISION_REST_ACTIVITY -> activitySettings.isEnableRestVisionNotification();
            case WORK_STANDING_ACTIVITY -> activitySettings.isEnableWorkStandingNotification();
            case WATER_INTAKE_ACTIVITY -> activitySettings.isEnableWaterIntakeNotification();
        };
    }

    public boolean adheresToUserNotificationPolicy(ZoneId userZoneId) {
        ZonedDateTime nowAtUserTimeZone = ZonedDateTime.now(userZoneId);

        DayOfWeek today = nowAtUserTimeZone.getDayOfWeek();
        Optional<NotificationPolicy> notificationPolicy = findByDay(today);

        if (notificationPolicy.isEmpty()) {
            return false;
        }

        ZonedDateTime notifyFrom = ZonedDateTime.of(LocalDate.now(userZoneId), notificationPolicy.get().getFrom(), userZoneId);
        ZonedDateTime notifyTo = ZonedDateTime.of(LocalDate.now(userZoneId), notificationPolicy.get().getTo(), userZoneId);

        if (notifyFrom.isAfter(notifyTo)) {
            // If the notification window spans midnight
            return nowAtUserTimeZone.isAfter(notifyFrom) || nowAtUserTimeZone.isBefore(notifyTo);
        } else {
            // Standard case: no cross-day scenario
            return nowAtUserTimeZone.isAfter(notifyFrom) && nowAtUserTimeZone.isBefore(notifyTo);
        }
    }

    private Optional<NotificationPolicy> findByDay(DayOfWeek dayOfWeek) {
        return notificationSettings.stream().filter(notificationPolicy -> notificationPolicy.getDayOfWeek().equals(dayOfWeek))
                .findFirst();
    }
}
