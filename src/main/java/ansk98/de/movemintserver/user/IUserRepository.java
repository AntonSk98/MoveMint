package ansk98.de.movemintserver.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository to manage {@link User}s.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IUserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user by its username.
     *
     * @param username username
     * @return user
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks whether there already exists a user by the given username.
     *
     * @param username username
     * @return true if the user already exists
     */
    boolean existsByUsername(String username);
}
