package ansk98.de.movemintserver.user;

import java.util.UUID;

public interface IUserService {

    UserDto findUserBy(String username);

    UserDto createUser(CreateUserCommand command);

    UserDto updateUser(UpdateUserCommand command);

    void resetPassword(ResetPasswordCommand command);
}
