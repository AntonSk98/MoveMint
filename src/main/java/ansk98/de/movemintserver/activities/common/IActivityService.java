package ansk98.de.movemintserver.activities.common;

import java.util.List;

/**
 * Service to manage activities.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IActivityService {

    /**
     * Finds all activities for authenticated user.
     *
     * @return all available activities for an authenticated user
     */
    List<ActivityDto> findActivities();

    /**
     * Accepts an activity.
     *
     * @param acceptCommand see {@link AcceptActivityCommand}
     */
    void acceptActivity(AcceptActivityCommand acceptCommand);

    /**
     * Declines an activity
     *
     * @param declineCommand see {@link RejectActivityCommand}
     */
    void rejectActivity(RejectActivityCommand declineCommand);
}
