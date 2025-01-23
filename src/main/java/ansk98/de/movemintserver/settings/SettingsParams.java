package ansk98.de.movemintserver.settings;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * Abstract command to define or update activity and notification settings.
 *
 * @param activitySettings     activity settings
 * @param notificationSettings notification settings
 */
public record SettingsParams(@Valid @NotNull ActivityDetails activitySettings,
                             @Valid @NotEmpty @Size(min = 7, max = 7) List<NotificationEntryDetail> notificationSettings) {

    public record ActivityDetails(boolean enabledStretchingNotification,
                                  boolean enableRestVisionNotification,
                                  boolean enableWaterIntakeNotification,
                                  boolean enableWorkStandingNotification) {

    }

    public record NotificationEntryDetail(@NotNull DayOfWeek dayOfWeek,
                                          @NotNull LocalTime from,
                                          @NotNull LocalTime to) {

    }
}
