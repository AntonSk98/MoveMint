package ansk98.de.movemintserver.activities.common;

import ansk98.de.movemintserver.activities.common.ActivityType;

import java.util.UUID;

/**
 * Command to decline an activity.
 *
 * @param id           activity id
 * @param activityType activity type
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record RejectActivityCommand(UUID id, ActivityType activityType) {
}
