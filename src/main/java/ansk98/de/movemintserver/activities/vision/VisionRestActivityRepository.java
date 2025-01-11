package ansk98.de.movemintserver.activities.vision;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository to manage {@link VisionRestActivity}s.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface VisionRestActivityRepository extends JpaRepository<VisionRestActivity, UUID> {
}
