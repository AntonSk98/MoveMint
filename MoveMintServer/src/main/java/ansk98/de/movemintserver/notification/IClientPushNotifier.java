package ansk98.de.movemintserver.notification;

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
     */
    void notifyClientBy(UserDeviceToken userDeviceToken);
}
