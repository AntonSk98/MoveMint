package ansk98.de.movemintserver.user;

import java.time.LocalDate;
import java.util.UUID;

public class UserDto {
    private final UUID id;
    private final String email;
    private final LocalDate dateOfBirth;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.dateOfBirth = user.getDateOfBirth();
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
