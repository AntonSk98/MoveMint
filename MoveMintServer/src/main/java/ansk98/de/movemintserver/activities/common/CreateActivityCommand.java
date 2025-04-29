package ansk98.de.movemintserver.activities.common;

import ansk98.de.movemintserver.activities.common.IActivity;

/**
 * Command to create a new activity.
 *
 * @param activity activity
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record CreateActivityCommand(IActivity activity) {
}
