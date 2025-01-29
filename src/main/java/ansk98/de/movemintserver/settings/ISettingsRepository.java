package ansk98.de.movemintserver.settings;

import ansk98.de.movemintserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository to manage {@link Settings}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface ISettingsRepository extends JpaRepository<Settings, UUID> {

    Settings findByUser(User User);

    Settings findByUserIdentity(String identity);
}
