package ansk98.de.movemintserver.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreateUserCommand(String password, @Email String email, @Past LocalDate dateOfBirth) {
}
