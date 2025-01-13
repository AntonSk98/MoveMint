package ansk98.de.movemintserver.activities.common;

import java.util.Arrays;
import java.util.List;

public class ActivityService implements IActivityService {

    private final List<IActivityService> activityServices;

    public ActivityService(List<IActivityService> activityServices) {
        this.activityServices = activityServices;
    }

    @Override
    public List<ActivityDto> findActivities() {
        return List.of();
    }

    @Override
    public void acceptActivity(AcceptActivityCommand acceptCommand) {
        validateActivityType(acceptCommand.activityType());
    }

    @Override
    public void rejectActivity(RejectActivityCommand declineCommand) {
        validateActivityType(declineCommand.activityType());
    }

    private static void validateActivityType(ActivityType activityType) {
        Arrays.stream(ActivityType.values())
                .filter(activityType::equals)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid activity type: " + activityType));
    }
}
