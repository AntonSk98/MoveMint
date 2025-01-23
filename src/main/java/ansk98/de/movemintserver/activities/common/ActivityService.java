package ansk98.de.movemintserver.activities.common;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of {@link IActivityService}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class ActivityService implements IActivityService {

    private final List<IActivityHandler> activityServices;

    public ActivityService(List<IActivityHandler> activityServices) {
        this.activityServices = activityServices;
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
