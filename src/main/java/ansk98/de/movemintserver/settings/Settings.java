package ansk98.de.movemintserver.settings;

import ansk98.de.movemintserver.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Collections;
import java.util.List;
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
}
