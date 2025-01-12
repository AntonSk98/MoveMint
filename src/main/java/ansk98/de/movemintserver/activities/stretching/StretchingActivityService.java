package ansk98.de.movemintserver.activities.stretching;

import ansk98.de.movemintserver.activities.common.*;
import ansk98.de.movemintserver.user.IUserService;
import ansk98.de.movemintserver.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

import static ansk98.de.movemintserver.activities.common.ActivityType.STRETCHING_ACTIVITY;

/**
 * Implementation of {@link IActivityService} for {@link StretchingActivity}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class StretchingActivityService implements ISupportedActivityService {

    private final IStretchingActivityRepository stretchingActivityRepository;
    private final IUserService userService;
    private final ActivitiesMetadata activitiesMetadata;

    public StretchingActivityService(IStretchingActivityRepository stretchingActivityRepository,
                                     IUserService userService,
                                     ActivitiesMetadata activitiesMetadata) {
        this.stretchingActivityRepository = stretchingActivityRepository;
        this.userService = userService;
        this.activitiesMetadata = activitiesMetadata;
    }

    @Override
    public List<ActivityDto> findActivities() {
        User user = userService.requireUser(Function.identity());
        ActivitiesMetadata.Metadata metadata = activitiesMetadata.findByType(STRETCHING_ACTIVITY);
        Function<StretchingActivity, ActivityDto> mapper = activity -> new ActivityDto()
                .setId(activity.getId())
                .setTitle(metadata.title())
                .setDescription(metadata.description())
                .setActivityType(STRETCHING_ACTIVITY)
                .setCreatedAt(activity.getCreatedAt())
                .setActivityDetails(new ActivityDetail().addDetail("exercises", activity.getExercises()));

        return stretchingActivityRepository
                .findAllByUser(user)
                .stream()
                .map(mapper)
                .toList();
    }

    @Override
    public void acceptActivity(AcceptActivityCommand acceptCommand) {
    }

    @Override
    public void declineActivity(DeclineActivityCommand declineCommand) {
    }

    @Override
    public boolean isSupported(ActivityType activityType) {
        return STRETCHING_ACTIVITY.equals(activityType);
    }
}
