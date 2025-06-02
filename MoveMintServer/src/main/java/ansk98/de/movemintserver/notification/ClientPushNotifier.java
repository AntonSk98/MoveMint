package ansk98.de.movemintserver.notification;

import ansk98.de.movemintserver.activities.common.ActivityType;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IClientPushNotifier}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
@ConditionalOnProperty(value = "firebase.enabled", matchIfMissing = true)
public class ClientPushNotifier implements IClientPushNotifier {

    private final FirebaseMessaging firebaseMessaging;
    private final NotificationProperties notificationProperties;

    /**
     * Constructor.
     *
     * @param firebaseMessaging      See {@link FirebaseMessaging}
     * @param notificationProperties See {@link NotificationProperties}
     */
    public ClientPushNotifier(FirebaseMessaging firebaseMessaging,
                              NotificationProperties notificationProperties) {
        this.firebaseMessaging = firebaseMessaging;
        this.notificationProperties = notificationProperties;
    }

    @Override
    public void notifyClientBy(UserDeviceToken userDeviceToken, ActivityType activityType) {
        if (userDeviceToken == null) {
            return;
        }
        Message message = Message.builder()
                .setToken(userDeviceToken.getDeviceToken())
                .putData("message", notificationProperties.getNotificationMessage(activityType))
                .build();

        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
