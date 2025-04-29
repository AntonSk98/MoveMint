package ansk98.de.movemintserver.activities.common;

import ansk98.de.movemintserver.activities.common.ActivityType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.function.Predicate;

/**
 * Constant metadata for activities.
 *
 * @param metadata for activities
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@ConfigurationProperties("activities")
public record ActivitiesMetadata(List<Metadata> metadata) {

    public Metadata findByType(ActivityType type) {
        Predicate<Metadata> findByType = metadata -> {
            ActivityType activityType = ActivityType.valueOf(metadata.type);
            return activityType.equals(type);
        };

        return metadata.stream().filter(findByType).findFirst().orElseThrow(() -> new IllegalArgumentException("No metadata found for type: " + type));
    }

    public record Metadata(String type, String title, String description) {
    }
}
