package ansk98.de.movemintserver.activities.common;

import ansk98.de.movemintserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository skeleton to manage activities.
 *
 * @param <Entity> activity aggregate
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@NoRepositoryBean
public interface IActivityRepository<Entity extends IActivity> extends JpaRepository<Entity, UUID> {
    List<Entity> findAllByUser(User user);
    Optional<Entity> findFirstByUserIdentityOrderByCreatedAtDesc(String identity);
}
