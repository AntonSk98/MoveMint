package ansk98.de.movemintserver.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 * Command to update user details.
 *
 * @param identity
 * @param dateOfBirth
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record UpdateUserCommand(@Email String identity, @Past LocalDate dateOfBirth) {
}
