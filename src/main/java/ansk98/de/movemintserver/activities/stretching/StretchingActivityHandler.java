package ansk98.de.movemintserver.activities.stretching;

import ansk98.de.movemintserver.activities.common.*;
import ansk98.de.movemintserver.eventing.IEventPublisher;
import ansk98.de.movemintserver.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static ansk98.de.movemintserver.activities.common.ActivityType.STRETCHING_ACTIVITY;

/**
 * Implementation of {@link IActivityService} for {@link StretchingActivity}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class StretchingActivityHandler extends AbstractActivityHandler<StretchingActivity> {


    public StretchingActivityHandler(IActivityRepository<StretchingActivity> stretchingActivityRepository,
                                     IUserService userService,
                                     ActivitiesMetadata activitiesMetadata,
                                     IEventPublisher eventPublisher) {
        super(stretchingActivityRepository, userService, activitiesMetadata, eventPublisher);
    }

    @Override
    public StretchingActivity mapToActivity(CreateActivityCommand activityCommand) {
        return activityCommand.activity().map(StretchingActivity.class);
    }


    @Override
    public Function<StretchingActivity, ActivityDto> mapToActivityDto() {
        return activity -> new ActivityDto()
                .setId(activity.getId())
                .setTitle(activitiesMetadata.findByType(activity.getActivityType()).title())
                .setDescription(activitiesMetadata.findByType(activity.getActivityType()).description())
                .setActivityType(STRETCHING_ACTIVITY)
                .setCreatedAt(activity.getCreatedAt())
                .setActivityDetails(new ActivityDetail().addDetail("exercises", activity.getExercises()));
    }

    @Override
    public ActivityType getActivityType() {
        return STRETCHING_ACTIVITY;
    }
}
