package ansk98.de.movemintserver.activities.vision;

import ansk98.de.movemintserver.activities.common.AbstractActivity;
import ansk98.de.movemintserver.activities.common.ActivityType;
import ansk98.de.movemintserver.user.User;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;

import static ansk98.de.movemintserver.activities.common.ActivityType.VISION_REST_ACTIVITY;

/**
 * Activity to release some strain from eyes.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Entity
public class VisionRestActivity extends AbstractActivity {

    protected VisionRestActivity() {

    }

    private VisionRestActivity(User user) {
        super(user);
    }

    public static VisionRestActivity createFor(@Nonnull User user) {
        return new VisionRestActivity(user);
    }

    @Override
    public ActivityType getActivityType() {
        return VISION_REST_ACTIVITY;
    }
}
