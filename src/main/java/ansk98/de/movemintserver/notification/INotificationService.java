package ansk98.de.movemintserver.notification;

/**
 * Service to manage user notifications.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface INotificationService {

    void sendStretchingActivityNotification();

    void sendVisionRestActivity();

    void sendWaterIntakeActivity();

    void sendWorkStandingActivity();
}
