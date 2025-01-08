package ansk98.de.movemintserver.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
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

    @Email(message = "A valid username must be provided")
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    protected User() {
    }

    /**
     * Creates a new user
     *
     * @param password    encoded password
     * @param username    username
     * @param dateOfBirth date of birth
     * @return user
     */
    public static User createUser(String password, String username, LocalDate dateOfBirth) {
        User user = new User();
        user.id = UUID.randomUUID();
        user.password = password;
        user.username = username;
        user.dateOfBirth = dateOfBirth;
        return user;
    }

    /**
     * Updates user details
     *
     * @param username    username
     * @param dateOfBirth date of birth
     */
    public void updateUser(String username, LocalDate dateOfBirth) {
        this.username = username;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Resets the password
     *
     * @param newPassword encoded password
     */
    public void resetPassword(String newPassword) {
        this.password = newPassword;
    }

    public UUID getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
