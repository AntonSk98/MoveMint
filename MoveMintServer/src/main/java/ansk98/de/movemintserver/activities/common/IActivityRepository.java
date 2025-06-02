package ansk98.de.movemintserver.activities.common;

import ansk98.de.movemintserver.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    default Optional<Entity> findLatestActivityByUserIdentity(String identity) {
        return findActivities(identity, PageRequest.of(0, 1)).stream().findFirst();
    }

    @Query("SELECT activity FROM #{#entityName} activity WHERE activity.user.identity = :identity ORDER BY activity.createdAt DESC")
    Page<Entity> findActivities(String identity, Pageable pageable);

    @Query("SELECT activity.id FROM #{#entityName} activity WHERE activity.user.identity = :identity")
    List<UUID> findActivityIdsBy(String identity);
}
