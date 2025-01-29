package ansk98.de.movemintserver.notification;

import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IClientPushNotifier}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class ClientPushNotifier implements IClientPushNotifier {

    @Override
    public void notifyClientBy(UserDeviceToken userDeviceToken) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
