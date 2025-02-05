package ansk98.de.movemintserver.activities.common;

import ansk98.de.movemintserver.eventing.user.BeforeUserDeletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link IActivityService}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class ActivityService implements IActivityServiceDelegate {

    private final List<IActivityHandler> activityServices;

    public ActivityService(List<IActivityHandler> activityServices) {
        this.activityServices = activityServices;
    }

    @EventListener(BeforeUserDeletedEvent.class)
    public void onBeforeUserDeleted(BeforeUserDeletedEvent event) {
        activityServices.forEach(activityService -> activityService.deleteActivities(event.getIdentity()));
    }

    @Override
    public List<ActivityDto> findActivities() {
        return activityServices.stream()
                .flatMap(activityService -> activityService.findActivities().stream())
                .toList();
    }

    @Override
    public void createActivity(CreateActivityCommand activityCommand) {
        findDelegate(activityCommand.activity().getActivityType()).createActivity(activityCommand);
    }

    @Override
    public void acceptActivity(AcceptActivityCommand acceptCommand) {
        validateActivityType(acceptCommand.activityType());
        findDelegate(acceptCommand.activityType()).acceptActivity(acceptCommand);
    }

    @Override
    public void rejectActivity(RejectActivityCommand declineCommand) {
        validateActivityType(declineCommand.activityType());
        findDelegate(declineCommand.activityType()).rejectActivity(declineCommand);
    }

    @Override
    public Optional<ActivityDto> findLatestActivity(String identity, ActivityType activityType) {
        return findDelegate(activityType).findLatestActivity(identity);
    }

    private static void validateActivityType(ActivityType activityType) {
        Arrays.stream(ActivityType.values())
                .filter(activityType::equals)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid activity type: " + activityType));
    }

    private IActivityHandler findDelegate(ActivityType activityType) {
        return activityServices.stream()
                .filter(service -> service.isSupported(activityType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No handler supported for: " + activityType));
    }
}
