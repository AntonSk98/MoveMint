package ansk98.de.movemintserver.eventing.activity;

import ansk98.de.movemintserver.activities.common.ActivityType;

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

    public ZonedDateTime getRejectedAt() {
        return rejectedAt;
    }
}
