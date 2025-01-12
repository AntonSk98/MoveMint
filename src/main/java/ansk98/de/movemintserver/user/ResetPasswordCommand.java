package ansk98.de.movemintserver.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * Command to reset a password.
 *
 * @param identity    identity
 * @param oldPassword old password
 * @param password    password
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record ResetPasswordCommand(@NotNull @Email String identity,
                                   @NotNull String oldPassword,
                                   @NotNull String password) {
}
