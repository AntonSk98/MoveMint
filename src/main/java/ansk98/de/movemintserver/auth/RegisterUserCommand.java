package ansk98.de.movemintserver.auth;

import ansk98.de.movemintserver.user.UserDetailsParams;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * Command to register a new user.
 *
 * @param identity    identity
 * @param password    plain-text password
 * @param userDetails user details
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record RegisterUserCommand(@Email String identity,
                                  String password,
                                  @Valid @NotNull UserDetailsParams userDetails) {
}
