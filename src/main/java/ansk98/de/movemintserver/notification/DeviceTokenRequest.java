package ansk98.de.movemintserver.notification;

import jakarta.validation.constraints.NotNull;

/**
 * Request to save a unique device token which is used to send personalized notifications.
 *
 * @param deviceToken device token
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record DeviceTokenRequest(@NotNull String deviceToken) {
}
