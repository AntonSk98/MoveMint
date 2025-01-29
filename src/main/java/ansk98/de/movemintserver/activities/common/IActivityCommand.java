package ansk98.de.movemintserver.activities.common;

/**
 * Service that is responsible for creating activities.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IActivityCommand {

    void createForUserIdentifiedBy(String identity, ActivityType activityType);
}
