package ansk98.de.movemintserver.activities.vision;

import ansk98.de.movemintserver.activities.common.*;
import ansk98.de.movemintserver.eventing.IEventPublisher;
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
public class VisionRestActivityService extends AbstractActivityService<VisionRestActivity> {


    public VisionRestActivityService(IVisionRestActivityRepository visionRestActivityRepository,
                                     IUserService userService,
                                     ActivitiesMetadata activitiesMetadata,
                                     IEventPublisher eventPublisher) {
        super(visionRestActivityRepository, userService, activitiesMetadata, eventPublisher);
    }

    @Override
    public Function<VisionRestActivity, ActivityDto> mapper() {
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
}
