package ansk98.de.movemintserver.notification;

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

    // if current_time (at user timezone) (9:29am) .isBefore() 'from_notify' (9am) + frequency (60m) + random_offset (5m) -> skip
    // if current_time (at user timezone) isAfter() 'to_notify'
}
