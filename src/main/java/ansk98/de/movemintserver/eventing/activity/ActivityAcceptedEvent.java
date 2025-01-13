package ansk98.de.movemintserver.eventing.activity;

import ansk98.de.movemintserver.activities.common.ActivityType;
import ansk98.de.movemintserver.activities.common.IActivity;

import java.time.ZonedDateTime;
import java.util.UUID;


/**
 * Event triggered if a user accepts an activity.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public class ActivityAcceptedEvent extends AbstractActivityEvent {

    private final ZonedDateTime acceptedAt;

    private ActivityAcceptedEvent(UUID id, ActivityType activityType, ZonedDateTime createdAt, String userIdentity) {
        super(id, activityType, createdAt, userIdentity);
        this.acceptedAt = ZonedDateTime.now();
    }

    public static ActivityAcceptedEvent of(IActivity activity) {
        return new ActivityAcceptedEvent(
                activity.getId(),
                activity.getActivityType(),
                activity.getCreatedAt(),
                activity.getUserIdentity()
        );
    }

    public ZonedDateTime getAcceptedAt() {
        return acceptedAt;
    }
}
