package ansk98.de.movemintserver.activities.waterintake;

import ansk98.de.movemintserver.activities.common.*;
import ansk98.de.movemintserver.eventing.IEventPublisher;
import ansk98.de.movemintserver.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Implementation of {@link IActivityService} for {@link WaterIntakeActivity}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class WaterIntakeActivityService extends AbstractActivityService<WaterIntakeActivity> {

    public WaterIntakeActivityService(IActivityRepository<WaterIntakeActivity> activityRepository,
                                      IUserService userService,
                                      ActivitiesMetadata activitiesMetadata,
                                      IEventPublisher eventPublisher) {
        super(activityRepository, userService, activitiesMetadata, eventPublisher);
    }

    @Override
    public Function<WaterIntakeActivity, ActivityDto> mapper() {
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
}
