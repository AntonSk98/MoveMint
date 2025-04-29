package ansk98.de.movemintserver.activities.workstanding;

import ansk98.de.movemintserver.activities.common.AbstractActivity;
import ansk98.de.movemintserver.activities.common.ActivityType;
import ansk98.de.movemintserver.user.User;
import jakarta.persistence.Entity;

import static ansk98.de.movemintserver.activities.common.ActivityType.WORK_STANDING_ACTIVITY;

/**
 * Activity to stand up and have some time working in a standing position.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Entity
public class WorkStandingActivity extends AbstractActivity {

    protected WorkStandingActivity() {

    }

    private WorkStandingActivity(User user) {
        super(user);
    }

    public static WorkStandingActivity createFor(User user) {
        return new WorkStandingActivity(user);
    }

    @Override
    public ActivityType getActivityType() {
        return WORK_STANDING_ACTIVITY;
    }
}
