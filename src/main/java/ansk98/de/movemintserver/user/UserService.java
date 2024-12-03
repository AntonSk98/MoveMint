package ansk98.de.movemintserver.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserBy(String email) {
        return userRepository
                .findByEmail(email)
                .map(UserDto::new)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    @Override
    @Transactional
    public UserDto createUser(CreateUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailTakenException("User with email " + command.email() + " already exists");
        }

        User user = User.createUser(passwordEncoder.encode(command.password()), command.email(), command.dateOfBirth());

        return Optional.of(userRepository.save(user))
                .map(UserDto::new)
                .get();
    }

    @Override
    @Transactional
    public UserDto updateUser(UpdateUserCommand command) {
        User user = userRepository
                .findById(command.identifier())
                .orElseThrow(() -> new UserNotFoundException("User with id " + command.identifier() + " not found"));
        user.updateUser(command.email(), command.dateOfBirth());

        return new UserDto(user);
    }

    @Override
    public void resetPassword(ResetPasswordCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new UserNotFoundException("User with email " + command.email() + " not found"));

        user.resetPassword(command.password());
    }
}
