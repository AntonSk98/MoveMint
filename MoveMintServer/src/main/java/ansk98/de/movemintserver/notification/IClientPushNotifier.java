package ansk98.de.movemintserver.notification;

import ansk98.de.movemintserver.activities.common.ActivityType;

/**
 * Client that is responsible for sending push notifications to the users.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IClientPushNotifier {

    /**
     * Notifies a user by sending a push notification using the unique device token.
     *
     * @param userDeviceToken unique device token
     * @param activityType activity type
     */
    void notifyClientBy(UserDeviceToken userDeviceToken, ActivityType activityType);
}
