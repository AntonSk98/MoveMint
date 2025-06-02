package ansk98.de.movemintserver.notification;

import ansk98.de.movemintserver.activities.common.ActivityType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

/**
 * Configuration that encapsulates the notification properties for each activity type.
 *
 * @param policy notification policy per activity type
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@ConfigurationProperties("notification")
public record NotificationProperties(List<NotificationPolicyEntry> policy, List<PushNotificationEntry> push) {

    public record PushNotificationEntry(ActivityType type, String message) {

    }

    public record NotificationPolicyEntry(ActivityType type, Duration frequency) {
    }

    public Duration getActivityFrequency(ActivityType type) {
        return policy
                .stream()
                .filter(notificationPolicyEntry -> type.equals(notificationPolicyEntry.type()))
                .findFirst()
                .map(NotificationPolicyEntry::frequency)
                .orElseThrow(() -> new IllegalStateException("No notification policy is found for type " + type));
    }

    public String getNotificationMessage(ActivityType type) {
        return push
                .stream()
                .filter(pushNotificationEntry -> type.equals(pushNotificationEntry.type))
                .findFirst()
                .map(PushNotificationEntry::message)
                .orElseThrow(() -> new IllegalStateException("No push notification message is found for type " + type));
    }
}
