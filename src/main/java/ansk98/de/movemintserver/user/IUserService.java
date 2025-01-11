package ansk98.de.movemintserver.user;

import ansk98.de.movemintserver.auth.RegisterUserCommand;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.function.Function;

/**
 * Service to manage users.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IUserService extends UserDetailsService {

    /**
     * Finds a user by identity and maps to the passed mapper.
     *
     * @param identity     identity
     * @param mapper       mapper
     * @param <MappedUser> mapper
     * @return mapped user
     */
    <MappedUser> MappedUser requireUser(String identity, Function<User, MappedUser> mapper);

    /**
     * Finds a user by identity
     *
     * @param identity identity
     * @return {@link UserDto}
     */
    UserDto findUserBy(String identity);

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
