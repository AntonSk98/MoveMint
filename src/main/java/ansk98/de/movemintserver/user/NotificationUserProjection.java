package ansk98.de.movemintserver.user;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Projection of a user to manage their notificatins.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface NotificationUserProjection {

    String getIdentity();

    String getTimezone();

    ZonedDateTime getRegisteredAt();

    default ZoneId timezoneAsZoneId() {
        return ZoneId.of(getTimezone());
    }
}
