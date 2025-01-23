package ansk98.de.movemintserver.eventing.activity;

import ansk98.de.movemintserver.activities.common.ActivityType;
import ansk98.de.movemintserver.activities.common.IActivity;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Event triggered if a user rejects an activity.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public class ActivityRejectedEvent extends AbstractActivityEvent {

    private final ZonedDateTime rejectedAt;

    protected ActivityRejectedEvent(UUID id, ActivityType activityType, ZonedDateTime createdAt, String userIdentity) {
        super(id, activityType, createdAt, userIdentity);
        this.rejectedAt = ZonedDateTime.now();
    }

    public static ActivityRejectedEvent of(IActivity activity) {
        return new ActivityRejectedEvent(
                activity.getId(),
                activity.getActivityType(),
                activity.getCreatedAt(),
                activity.getUserIdentity()
        );
    }

    public ZonedDateTime getRejectedAt() {
        return rejectedAt;
    }
}
