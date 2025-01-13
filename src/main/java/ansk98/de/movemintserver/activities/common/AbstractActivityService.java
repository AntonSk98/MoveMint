package ansk98.de.movemintserver.activities.common;

import ansk98.de.movemintserver.eventing.IEventPublisher;
import ansk98.de.movemintserver.user.IUserService;

import java.util.List;
import java.util.function.Function;

import static java.util.function.UnaryOperator.identity;

/**
 * Abstract base class for activity services.
 * Provides common behavior for managing activities such as finding, accepting, and rejecting them.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public abstract class AbstractActivityService<Activity extends AbstractActivity> implements ISupportedActivityService {

    protected final IActivityRepository<Activity> activityRepository;
    protected final IUserService userService;
    protected final ActivitiesMetadata activitiesMetadata;
    protected final IEventPublisher eventPublisher;

    public AbstractActivityService(IActivityRepository<Activity> activityRepository,
                                   IUserService userService,
                                   ActivitiesMetadata activitiesMetadata,
                                   IEventPublisher eventPublisher) {
        this.activityRepository = activityRepository;
        this.userService = userService;
        this.activitiesMetadata = activitiesMetadata;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<ActivityDto> findActivities() {
        return activityRepository.findAllByUser(userService.requireUser(identity()))
                .stream()
                .map(mapper())
                .toList();
    }

    @Override
    public void acceptActivity(AcceptActivityCommand acceptCommand) {
        Activity activity = activityRepository.getReferenceById(acceptCommand.id());
        eventPublisher.publishEvent(activity.accept());
        activityRepository.delete(activity);
    }

    @Override
    public void rejectActivity(RejectActivityCommand rejectCommand) {
        Activity activity = activityRepository.getReferenceById(rejectCommand.id());
        eventPublisher.publishEvent(activity.reject());
        activityRepository.delete(activity);
    }

    @Override
    public boolean isSupported(ActivityType activityType) {
        return getActivityType().equals(activityType);
    }

    public abstract Function<Activity, ActivityDto> mapper();

    public abstract ActivityType getActivityType();
}
