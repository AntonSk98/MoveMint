package ansk98.de.movemintserver.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * Command to authenticate a user.
 *
 * @param username username
 * @param password password
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record AuthenticateUserCommand(@NotNull @Email String username, @NotNull String password) {
}