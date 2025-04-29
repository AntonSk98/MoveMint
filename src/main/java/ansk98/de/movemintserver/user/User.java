package ansk98.de.movemintserver.user;

import ansk98.de.movemintserver.eventing.DomainEventAware;
import ansk98.de.movemintserver.eventing.user.BeforeUserDeletedEvent;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * User entity.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Entity
@Table(name = "users")
public class User extends DomainEventAware {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String password;

    @Email(message = "A valid identity must be provided")
    @Column(nullable = false, unique = true)
    private String identity;

    @Valid
    @Embedded
    private UserDetails userDetails;

    @Column(nullable = false)
    private ZonedDateTime registeredAt;

    protected User() {
    }

    /**
     * Creates a new user
     *
     * @param password    encoded password
     * @param identity    identity
     * @param userDetails user details
     * @return user
     */
    public static User createUser(String identity, String password, UserDetails.Builder userDetails) {
        User user = new User();
        user.id = UUID.randomUUID();
        user.password = password;
        user.identity = identity;
        user.userDetails = userDetails.build();
        user.registeredAt = ZonedDateTime.now();
        return user;
    }

    /**
     * Updates user details
     *
     * @param identity    identity
     * @param userDetails user details
     */
    public void updateUser(String identity, @NotNull UserDetails.Builder userDetails) {
        this.identity = identity;
        this.userDetails = userDetails.build();
    }

    /**
     * Resets the password
     *
     * @param passwordMatchesPredicate predicate
     * @param newPassword              encoded password
     */
    public void resetPassword(Predicate<String> passwordMatchesPredicate, String newPassword) {
        if (!passwordMatchesPredicate.test(this.password)) {
            throw new WrongCredentialsException();
        }
        this.password = newPassword;
    }

    /**
     * Publishes an event before a {@link User} is deleted.
     */
    public void onBeforeDeleted() {
        publish(new BeforeUserDeletedEvent(identity));
    }

    public UUID getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getIdentity() {
        return identity;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public ZonedDateTime getRegisteredAt() {
        return registeredAt;
    }
}
