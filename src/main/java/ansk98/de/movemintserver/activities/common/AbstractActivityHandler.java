package ansk98.de.movemintserver.activities.common;

import ansk98.de.movemintserver.auth.AuthenticationUtils;
import ansk98.de.movemintserver.user.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.function.UnaryOperator.identity;

/**
 * Abstract base class for activity services.
 * Provides common behavior for managing activities such as finding, accepting, and rejecting them.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public abstract class AbstractActivityHandler<Activity extends IActivity> implements IActivityHandler {

    protected final IActivityRepository<Activity> activityRepository;
    protected final IUserService userService;
    protected final ActivitiesMetadata activitiesMetadata;

    public AbstractActivityHandler(IActivityRepository<Activity> activityRepository,
                                   IUserService userService,
                                   ActivitiesMetadata activitiesMetadata) {
        this.activityRepository = activityRepository;
        this.userService = userService;
        this.activitiesMetadata = activitiesMetadata;
    }

    @Override
    public void createActivity(CreateActivityCommand createActivityCommand) {
        var activity = mapToActivity(createActivityCommand);

        activityRepository.save(activity);

    }

    @Override
    public List<ActivityDto> findActivities() {
        return activityRepository.findAllByUser(userService.requireUser(identity()))
                .stream()
                .map(mapToActivityDto())
                .toList();
    }

    @Override
    public void acceptActivity(AcceptActivityCommand acceptCommand) {
        Activity activity = activityRepository
                .findById(acceptCommand.id())
                .orElseThrow(ActivityOperationFailedException::new);
        validateAuthenticatedUserCanDo(activity.getUserIdentity());
        activity.accept();
        activityRepository.delete(activity);
    }

    @Override
    public void rejectActivity(RejectActivityCommand rejectCommand) {
        Activity activity = activityRepository
                .findById(rejectCommand.id())
                .orElseThrow(ActivityOperationFailedException::new);
        validateAuthenticatedUserCanDo(activity.getUserIdentity());
        activity.reject();
        activityRepository.delete(activity);
    }

    @Override
    public Optional<ActivityDto> findLatestActivity(String identity) {
        Optional<Activity> activityOptional = activityRepository.findLatestActivityByUserIdentity(identity);

        return activityOptional.map(activity -> mapToActivityDto().apply(activity));

    }

    @Override
    public boolean isSupported(ActivityType activityType) {
        return getActivityType().equals(activityType);
    }

    @Override
    @Transactional
    public void deleteActivities(String identity) {
        activityRepository.findActivityIdsBy(identity).forEach(activityRepository::deleteById);
    }

    public abstract Function<Activity, ActivityDto> mapToActivityDto();

    public abstract ActivityType getActivityType();

    public abstract Activity mapToActivity(CreateActivityCommand activityCommand);

    private static void validateAuthenticatedUserCanDo(String activityUsername) {
        if (!StringUtils.equals(activityUsername, AuthenticationUtils.requireUserIdentity())) {
            throw new ActivityOperationFailedException();
        }
    }

}
