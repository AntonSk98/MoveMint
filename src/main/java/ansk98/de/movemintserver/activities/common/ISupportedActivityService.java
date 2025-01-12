package ansk98.de.movemintserver.activities.common;

/**
 * Extension of {@link IActivityService} that adds functionality
 * to determine if a specific activity type is supported by the implementing service.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface ISupportedActivityService extends IActivityService {

    /**
     * Checks if the current service is able to handle the passed {@code activityType}.
     *
     * @param activityType activity type
     * @return true if supported, false otherwise
     */
    boolean isSupported(ActivityType activityType);
}
