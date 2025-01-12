package ansk98.de.movemintserver.activities.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Constant metadata for activities.
 *
 * @param metadata for activities
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@ConfigurationProperties("activities")
public record Activities(List<Metadata> metadata) {

    record Metadata(String type, String title, String description) {
    }
}
