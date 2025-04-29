package ansk98.de.movemintserver.activities.common;

import java.util.UUID;

/**
 * Command to accept an activity.
 *
 * @param id           activity identifier
 * @param activityType activity type
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record AcceptActivityCommand(UUID id, ActivityType activityType) {
}
