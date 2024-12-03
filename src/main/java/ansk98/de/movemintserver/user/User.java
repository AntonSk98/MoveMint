package ansk98.de.movemintserver.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String password;

    @Email(message = "A valid email address must be provided")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    protected User() {
    }

    public static User createUser(String password, String email, LocalDate dateOfBirth) {
        User user = new User();
        user.id = UUID.randomUUID();
        user.password = password;
        user.email = email;
        user.dateOfBirth = dateOfBirth;
        return user;
    }

    public void updateUser(String email, LocalDate dateOfBirth) {
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public void resetPassword(String newPassword) {
        this.password = newPassword;
    }

    public UUID getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
