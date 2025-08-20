package ansk98.de.movemintserver.user;

import java.time.LocalDate;
import java.time.ZoneId;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

/**
 * Object containing details of a user.
 *
 * @param name        name
 * @param dateOfBirth date of birth
 * @param gender      gender
 * @param height      height
 * @param weight      weight
 * @param timezone    timezone
 */
public record UserDetailsParams(@NotNull String name,
                                @Past @NotNull LocalDate dateOfBirth,
                                @NotNull UserDetails.Gender gender,
                                @NotNull Integer height,
                                @NotNull Integer weight,
                                @NotNull ZoneId timezone) {
}
