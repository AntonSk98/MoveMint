package ansk98.de.movemintserver.auth;

import jakarta.validation.constraints.NotNull;

/**
 * Command to refresh an access token.
 *
 * @param refreshToken refresh token
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record RefreshTokenCommand(@NotNull String refreshToken) {
}
