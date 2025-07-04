package ansk98.de.movemintserver.activities.common;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Activity represents an action that should be performed by the client.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IActivity {

    UUID getId();

    ZonedDateTime getCreatedAt();

    ActivityType getActivityType();

    String getUserIdentity();

    void accept();

    void reject();

    default <T extends IActivity> T map(Class<T> clazz) {
        if (this.getClass().equals(clazz)) {
            return clazz.cast(this);
        }
        throw new IllegalStateException("Unsupported type. Current type: " + this.getClass().getSimpleName() + " Passed type: " + clazz.getSimpleName());
    }
}
