package ansk98.de.movemintserver.activities.waterintake;

import ansk98.de.movemintserver.activities.common.AbstractActivity;
import ansk98.de.movemintserver.activities.common.ActivityType;
import ansk98.de.movemintserver.user.User;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;

/**
 * Activity to drink some water to stay hydrated.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Entity
public class WaterIntakeActivity extends AbstractActivity {

    /**
     * No-args.
     */
    protected WaterIntakeActivity() {

    }

    private WaterIntakeActivity(User user) {
        super(user);
    }

    public static WaterIntakeActivity createFor(@Nonnull User user) {
        return new WaterIntakeActivity(user);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATER_INTAKE_ACTIVITY;
    }
}
