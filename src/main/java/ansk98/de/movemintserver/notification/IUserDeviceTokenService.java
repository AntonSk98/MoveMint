package ansk98.de.movemintserver.notification;

/**
 * Service to persist the user device token.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IUserDeviceTokenService {

    void saveUserDeviceToken(DeviceTokenRequest deviceTokenRequest);
}
