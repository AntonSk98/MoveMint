package ansk98.de.movemintserver.activities.workstanding;

import ansk98.de.movemintserver.activities.common.*;
import ansk98.de.movemintserver.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static ansk98.de.movemintserver.activities.common.ActivityType.WORK_STANDING_ACTIVITY;


/**
 * Implementation of {@link IActivityService} for {@link WorkStandingActivity}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class WorkStandingActivityHandler extends AbstractActivityHandler<WorkStandingActivity> {

    public WorkStandingActivityHandler(IActivityRepository<WorkStandingActivity> activityRepository,
                                       IUserService userService,
                                       ActivitiesMetadata activitiesMetadata) {
        super(activityRepository, userService, activitiesMetadata);
    }

    @Override
    public Function<WorkStandingActivity, ActivityDto> mapToActivityDto() {
        return activity -> new ActivityDto()
                .setId(activity.getId())
                .setTitle(activitiesMetadata.findByType(getActivityType()).title())
                .setDescription(activitiesMetadata.findByType(getActivityType()).description())
                .setCreatedAt(activity.getCreatedAt())
                .setActivityType(getActivityType());
    }

    @Override
    public ActivityType getActivityType() {
        return WORK_STANDING_ACTIVITY;
    }

    @Override
    public WorkStandingActivity mapToActivity(CreateActivityCommand activityCommand) {
        return activityCommand.activity().map(WorkStandingActivity.class);
    }
}
