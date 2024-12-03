package ansk98.de.movemintserver.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateUserCommand(UUID identifier, @Email String email, @Past LocalDate dateOfBirth) {
}
