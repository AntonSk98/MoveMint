package ansk98.de.movemintserver.user;

import ansk98.de.movemintserver.auth.AuthenticationUtils;
import ansk98.de.movemintserver.auth.RegisterUserCommand;
import ansk98.de.movemintserver.eventing.IEventPublisher;
import ansk98.de.movemintserver.eventing.user.BeforeUserDeletedEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

import static ansk98.de.movemintserver.auth.AuthenticationUtils.ensureCanActOnBehalfOf;

/**
 * Implementation of {@link IUserService}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IEventPublisher eventPublisher;

    public UserService(IUserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       IEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public <MappedUser> MappedUser requireUser(Function<User, MappedUser> mapper) {
        return mapper.apply(userRepository
                .findByIdentity(AuthenticationUtils.requireUserIdentity())
                .orElseThrow(() -> new UserNotFoundException("No user is authenticated"))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserBy(String identity) {
        return userRepository
                .findByIdentity(identity)
                .map(UserDto::from)
                .orElseThrow(() -> new UserNotFoundException("User with identity " + identity + " not found"));
    }

    @Override
    @Transactional
    public UserDto createUser(RegisterUserCommand command) {
        if (userRepository.existsByIdentity(command.identity())) {
            throw new IdentityTakenException("User with identity " + command.identity() + " already exists");
        }

        User user = User.createUser(
                command.identity(),
                passwordEncoder.encode(command.password()),
                new UserDetails.Builder()
                        .name(command.userDetails().name())
                        .dateOfBirth(command.userDetails().dateOfBirth())
                        .gender(command.userDetails().gender())
                        .height(command.userDetails().height())
                        .weight(command.userDetails().weight())
                        .timezone(command.userDetails().timezone())
        );

        return Optional.of(userRepository.save(user))
                .map(UserDto::from)
                .get();
    }

    @Override
    @Transactional
    public UserDto updateUser(UpdateUserCommand command) {
        User user = userRepository
                .findByIdentity(command.identity())
                .orElseThrow(() -> new UserNotFoundException("User with identity " + command.identity() + " not found"));

        ensureCanActOnBehalfOf(user.getIdentity());

        user.updateUser(command.identity(), new UserDetails.Builder()
                .name(command.userDetails().name())
                .dateOfBirth(command.userDetails().dateOfBirth())
                .gender(command.userDetails().gender())
                .height(command.userDetails().height())
                .weight(command.userDetails().weight())
                .timezone(command.userDetails().timezone()));

        return UserDto.from(user);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordCommand command) {
        ensureCanActOnBehalfOf(command.identity());

        User user = userRepository.findByIdentity(command.identity())
                .orElseThrow(() -> new UserNotFoundException("User with identity " + command.identity() + " not found"));


        user.resetPassword(
                oldPassword -> passwordEncoder.matches(command.oldPassword(), oldPassword),
                passwordEncoder.encode(command.password())
        );
    }

    @Override
    @Transactional
    public void deleteUser(String identity) {
        User toBeDeletedUser = userRepository
                .findByIdentity(identity)
                .orElseThrow(() -> new UserNotFoundException("User with identity " + identity + " not found"));
        eventPublisher.publishEvent(new BeforeUserDeletedEvent(toBeDeletedUser.getIdentity()));

        userRepository.delete(toBeDeletedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String identity) throws UsernameNotFoundException {
        User user = userRepository.findByIdentity(identity).orElseThrow(() -> new UserNotFoundException("User not found with identity " + identity));
        return new org.springframework.security.core.userdetails.User(user.getIdentity(), user.getPassword(), Collections.emptyList());
    }
}
