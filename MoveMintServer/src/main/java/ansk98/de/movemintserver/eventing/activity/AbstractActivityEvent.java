package ansk98.de.movemintserver.eventing.activity;

import ansk98.de.movemintserver.activities.common.ActivityType;
import ansk98.de.movemintserver.eventing.IEvent;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Abstract activity event.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public abstract class AbstractActivityEvent implements IEvent {

    private final UUID id;
    private final ActivityType activityType;
    private final ZonedDateTime createdAt;
    private final String identity;

    protected AbstractActivityEvent(UUID id, ActivityType activityType, ZonedDateTime createdAt, String identity) {
        this.id = id;
        this.activityType = activityType;
        this.createdAt = createdAt;
        this.identity = identity;
    }

    public UUID getId() {
        return id;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getIdentity() {
        return identity;
    }
}
