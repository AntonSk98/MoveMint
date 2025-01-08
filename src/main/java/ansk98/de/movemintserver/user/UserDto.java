package ansk98.de.movemintserver.user;

import java.time.LocalDate;

/**
 * User DTO.
 *
 * @param username    username
 * @param dateOfBirth date of birth
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record UserDto(String username, LocalDate dateOfBirth) {

    public static UserDto from(User user) {
        return new UserDto(user.getUsername(), user.getDateOfBirth());
    }
}
