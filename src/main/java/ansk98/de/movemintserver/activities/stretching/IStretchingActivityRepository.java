package ansk98.de.movemintserver.activities.stretching;

import ansk98.de.movemintserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


/**
 * Repository to manage {@link StretchingActivity}s.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IStretchingActivityRepository extends JpaRepository<StretchingActivity, UUID> {
    List<StretchingActivity> findAllByUser(User user);
}
