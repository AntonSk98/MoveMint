package ansk98.de.movemintserver.notification;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository to manage {@link UserDeviceToken}s.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IUserDeviceTokenRepository extends Repository<UserDeviceToken, UUID> {

    Optional<UserDeviceToken> findByUserIdentity(String identity);

    void save(UserDeviceToken userDeviceToken);
}
