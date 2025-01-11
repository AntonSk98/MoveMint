package ansk98.de.movemintserver.activities.waterintake;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository to manage {@link WaterIntakeActivity}s.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface WaterIntakeActivityRepository extends JpaRepository<WaterIntakeActivity, UUID> {
}
