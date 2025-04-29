package ansk98.de.movemintserver.activities.waterintake;

import ansk98.de.movemintserver.activities.common.*;
import ansk98.de.movemintserver.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Implementation of {@link IActivityService} for {@link WaterIntakeActivity}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class WaterIntakeActivityHandler extends AbstractActivityHandler<WaterIntakeActivity> {

    public WaterIntakeActivityHandler(IActivityRepository<WaterIntakeActivity> activityRepository,
                                      IUserService userService,
                                      ActivitiesMetadata activitiesMetadata) {
        super(activityRepository, userService, activitiesMetadata);
    }

    @Override
    public Function<WaterIntakeActivity, ActivityDto> mapToActivityDto() {
        return activity -> new ActivityDto()
                .setId(activity.getId())
                .setTitle(activitiesMetadata.findByType(getActivityType()).title())
                .setDescription(activitiesMetadata.findByType(getActivityType()).description())
                .setCreatedAt(activity.getCreatedAt())
                .setActivityType(getActivityType());
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATER_INTAKE_ACTIVITY;
    }

    @Override
    public WaterIntakeActivity mapToActivity(CreateActivityCommand activityCommand) {
        return activityCommand.activity().map(WaterIntakeActivity.class);
    }
}
