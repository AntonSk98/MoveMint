package ansk98.de.movemintserver.user;

import java.time.LocalDate;

/**
 * User DTO.
 *
 * @param identity    identity
 * @param dateOfBirth date of birth
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record UserDto(String identity, LocalDate dateOfBirth) {

    public static UserDto from(User user) {
        return new UserDto(user.getIdentity(), user.getDateOfBirth());
    }
}
