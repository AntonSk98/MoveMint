package ansk98.de.movemintserver.notification;

import ansk98.de.movemintserver.notification.IActivityNotificationScheduler;
import ansk98.de.movemintserver.notification.INotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link IActivityNotificationScheduler}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Component
public class ActivityNotificationScheduler implements IActivityNotificationScheduler {

    private final INotificationService notificationService;

    public ActivityNotificationScheduler(INotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @Override
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void sendStretchingActivityNotification() {
        notificationService.sendStretchingActivityNotification();
    }

    @Override
    public void removeObsoleteActivities() {

    }
}
