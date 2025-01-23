package ansk98.de.movemintserver.activities.common;

/**
 * Extension of {@link IActivityService} that adds functionality
 * to determine if a specific activity type is supported by the implementing service.
 * <p>
 * This interface is intended to be implemented by services that handle different types of activities,
 * enabling them to declare support for specific {@link ActivityType}s.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IActivityHandler extends IActivityService {

    /**
     * Checks if the current service is able to handle the passed {@code activityType}.
     *
     * @param activityType activity type
     * @return true if supported, false otherwise
     */
    boolean isSupported(ActivityType activityType);
}
