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
     * Finds a user by its identity.
     *
     * @param identity identity
     * @return user
     */
    Optional<User> findByIdentity(String identity);

    /**
     * Checks whether there already exists a user by the given identity.
     *
     * @param identity identity
     * @return true if the user already exists
     */
    boolean existsByIdentity(String identity);
}
