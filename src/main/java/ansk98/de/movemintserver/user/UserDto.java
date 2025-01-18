package ansk98.de.movemintserver.user;

/**
 * User DTO.
 *
 * @param identity          identity
 * @param userDetailsParams user details
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record UserDto(String identity, UserDetailsParams userDetailsParams) {

    public static UserDto from(User user) {
        return new UserDto(
                user.getIdentity(),
                new UserDetailsParams(
                        user.getUserDetails().getName(),
                        user.getUserDetails().getDateOfBirth(),
                        user.getUserDetails().getGender(),
                        user.getUserDetails().getHeight(),
                        user.getUserDetails().getWeight(),
                        user.getUserDetails().getTimezone()
                ));
    }
}
