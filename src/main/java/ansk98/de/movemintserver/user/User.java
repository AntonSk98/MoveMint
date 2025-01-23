package ansk98.de.movemintserver.user;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import java.util.UUID;

/**
 * User entity.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Entity
@Table(name = "users")
public class User {

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
        return user;
    }

    /**
     * Updates user details
     *
     * @param identity    identity
     * @param userDetails user details
     */
    public void updateUser(String identity, UserDetails.Builder userDetails) {
        this.identity = identity;
        this.userDetails = userDetails.build();
    }

    /**
     * Resets the password
     *
     * @param oldPassword old password
     * @param newPassword encoded password
     */
    public void resetPassword(String oldPassword, String newPassword) {
        if (!this.password.equals(oldPassword)) {
            throw new WrongCredentialsException();
        }
        this.password = newPassword;
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
}
