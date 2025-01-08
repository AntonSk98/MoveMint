package ansk98.de.movemintserver.user;

import ansk98.de.movemintserver.auth.RegisterUserCommand;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Service to manage users.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IUserService extends UserDetailsService {

    /**
     * Finds a user by username
     *
     * @param username username
     * @return {@link UserDto}
     */
    UserDto findUserBy(String username);

    /**
     * Creates a user.
     *
     * @param command {@link RegisterUserCommand}
     * @return {@link UserDto}
     */
    UserDto createUser(RegisterUserCommand command);

    /**
     * Updates user details.
     *
     * @param command {@link UpdateUserCommand}
     * @return {@link UserDto}
     */
    UserDto updateUser(UpdateUserCommand command);

    /**
     * Resets user password
     *
     * @param command {@link ResetPasswordCommand}
     */
    void resetPassword(ResetPasswordCommand command);
}
