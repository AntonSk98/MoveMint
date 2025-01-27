package ansk98.de.movemintserver.notification;

/**
 * A scheduler service responsible for analyzing user activity and managing notifications.
 *
 * @author Anton Skripin (anton.tech98@telekom.de)
 */
public interface INotificationScheduler {

    /**
     * Sends notifications to users based on their recent activities.
     */
    void notifyAboutActivity();

    /**
     * Removes activities that are deemed obsolete or no longer relevant.
     */
    void removeObsoleteActivities();
}
