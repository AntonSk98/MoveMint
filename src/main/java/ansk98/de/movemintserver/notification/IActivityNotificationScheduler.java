package ansk98.de.movemintserver.notification;

import ansk98.de.movemintserver.activities.common.ActivityType;

/**
 * A scheduler service responsible for analyzing user activity and managing notifications.
 *
 * @author Anton Skripin (anton.tech98@telekom.de)
 */
public interface IActivityNotificationScheduler {

    /**
     * Sends {@link ActivityType#STRETCHING_ACTIVITY} notifications to users based on their recent activities.
     */
    void sendStretchingActivityNotification();

    /**
     * Removes activities that are deemed obsolete or no longer relevant.
     */
    void removeObsoleteActivities();
}
