package ansk98.de.movemintserver.notification;

import ansk98.de.movemintserver.activities.common.ActivityType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

/**
 * Configuration that encapsulates the notification policy for each activity type.
 *
 * @param policy notification policy per activity type
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@ConfigurationProperties("notification")
public record NotificationPolicy(List<NotificationPolicyEntry> policy) {

    public record NotificationPolicyEntry(ActivityType type, Duration frequency) {
    }

    public Duration getActivityFrequency(ActivityType type) {
        return policy.stream().filter(notificationPolicyEntry -> type.equals(notificationPolicyEntry.type()))
                .findFirst()
                .map(NotificationPolicyEntry::frequency)
                .orElseThrow(() -> new IllegalStateException("No notification policy is found for type " + type));
    }
}
