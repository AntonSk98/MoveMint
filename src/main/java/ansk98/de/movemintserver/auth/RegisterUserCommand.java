package ansk98.de.movemintserver.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 * Command to register a new user.
 *
 * @param password    plain-text password
 * @param identity    identity
 * @param dateOfBirth date of birth
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record RegisterUserCommand(String password, @Email String identity, @Past @NotNull LocalDate dateOfBirth) {
}
