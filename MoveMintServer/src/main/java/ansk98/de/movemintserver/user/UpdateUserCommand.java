package ansk98.de.movemintserver.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * Command to update user details.
 *
 * @param identity          identity
 * @param userDetails user details
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record UpdateUserCommand(@Email String identity,
                                @Valid @NotNull UserDetailsParams userDetails) {
}
