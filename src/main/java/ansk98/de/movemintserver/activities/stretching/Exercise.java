package ansk98.de.movemintserver.activities.stretching;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

/**
 * Stretching exercise.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Entity
public class Exercise {

    @Id
    private UUID id;
    @Column
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String link;

    /**
     * No-args.
     */
    protected Exercise() {

    }

    private Exercise(String name, String description, String link) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.link = link;
    }

    public static Exercise create(String name, String description, String link) {
        return new Exercise(name, description, link);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }
}
