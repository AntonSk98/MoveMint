package ansk98.de.movemintserver.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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


    /**
     * Streams all users with the only necessary details for managing notifications.
     *
     * @return stream of all user details
     */
    @Query("SELECT u.identity AS identity, u.userDetails.timezone AS timezone, u.registeredAt AS registeredAt FROM User u")
    Stream<NotificationUserProjection> streamUserDetails();
}
