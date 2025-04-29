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

    /**
     * No-args.
     */
    protected Exercise() {

    }

    private Exercise(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }

    public static Exercise create(String name, String description) {
        return new Exercise(name, description);
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
}
