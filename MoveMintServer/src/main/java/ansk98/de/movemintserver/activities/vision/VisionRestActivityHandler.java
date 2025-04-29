package ansk98.de.movemintserver.activities.vision;

import ansk98.de.movemintserver.activities.common.*;
import ansk98.de.movemintserver.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static ansk98.de.movemintserver.activities.common.ActivityType.VISION_REST_ACTIVITY;

/**
 * Implementation of {@link IActivityService} for {@link VisionRestActivity}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class VisionRestActivityHandler extends AbstractActivityHandler<VisionRestActivity> {


    public VisionRestActivityHandler(IVisionRestActivityRepository visionRestActivityRepository,
                                     IUserService userService,
                                     ActivitiesMetadata activitiesMetadata) {
        super(visionRestActivityRepository, userService, activitiesMetadata);
    }

    @Override
    public Function<VisionRestActivity, ActivityDto> mapToActivityDto() {
        return activity -> new ActivityDto()
                .setId(activity.getId())
                .setTitle(activitiesMetadata.findByType(activity.getActivityType()).title())
                .setDescription(activitiesMetadata.findByType(activity.getActivityType()).description())
                .setActivityType(VISION_REST_ACTIVITY)
                .setCreatedAt(activity.getCreatedAt());
    }

    @Override
    public ActivityType getActivityType() {
        return VISION_REST_ACTIVITY;
    }

    @Override
    public VisionRestActivity mapToActivity(CreateActivityCommand activityCommand) {
        return activityCommand.activity().map(VisionRestActivity.class);
    }
}
