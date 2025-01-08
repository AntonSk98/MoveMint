package ansk98.de.movemintserver.user;

import ansk98.de.movemintserver.auth.RegisterUserCommand;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * Implementation of {@link IUserService}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
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
    public UserDto findUserBy(String username) {
        return userRepository
                .findByUsername(username)
                .map(UserDto::from)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
    }

    @Override
    @Transactional
    public UserDto createUser(RegisterUserCommand command) {
        if (userRepository.existsByUsername(command.username())) {
            throw new UsernameTakenException("User with username " + command.username() + " already exists");
        }

        User user = User.createUser(passwordEncoder.encode(command.password()), command.username(), command.dateOfBirth());

        return Optional.of(userRepository.save(user))
                .map(UserDto::from)
                .get();
    }

    @Override
    @Transactional
    public UserDto updateUser(UpdateUserCommand command) {
        User user = userRepository
                .findByUsername(command.username())
                .orElseThrow(() -> new UserNotFoundException("User with username " + command.username() + " not found"));
        user.updateUser(command.username(), command.dateOfBirth());

        return UserDto.from(user);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordCommand command) {
        User user = userRepository.findByUsername(command.username())
                .orElseThrow(() -> new UserNotFoundException("User with username " + command.username() + " not found"));

        user.resetPassword(command.password());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found with username " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
