package ansk98.de.movemintserver.activities.common;

import java.util.Optional;

public interface IActivityServiceDelegate extends IActivityService {

    /**
     * Finds the latest published activity of the passed type for a user.
     *
     * @param identity     identity
     * @param activityType activity type
     * @return latest activity
     */
    Optional<ActivityDto> findLatestActivity(String identity, ActivityType activityType);

}
