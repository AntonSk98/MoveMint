package ansk98.de.movemintserver.activities.workstanding;

import ansk98.de.movemintserver.activities.common.*;
import ansk98.de.movemintserver.eventing.IEventPublisher;
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
public class WorkStandingActivityService extends AbstractActivityService<WorkStandingActivity> {

    public WorkStandingActivityService(IActivityRepository<WorkStandingActivity> activityRepository,
                                       IUserService userService,
                                       ActivitiesMetadata activitiesMetadata,
                                       IEventPublisher eventPublisher) {
        super(activityRepository, userService, activitiesMetadata, eventPublisher);
    }

    @Override
    public Function<WorkStandingActivity, ActivityDto> mapper() {
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
}
