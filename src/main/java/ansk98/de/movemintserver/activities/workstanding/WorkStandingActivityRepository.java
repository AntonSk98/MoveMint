package ansk98.de.movemintserver.activities.workstanding;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository to manage {@link WorkStandingActivity}s.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface WorkStandingActivityRepository extends JpaRepository<WorkStandingActivity, UUID> {
}
