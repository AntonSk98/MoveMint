package ansk98.de.movemintserver.activities.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * DTO encapsulating activity properties.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public class ActivityDto {
    private UUID id;
    private String title;
    private String description;
    private ZonedDateTime createdAt;
    private ActivityType activityType;

    @JsonUnwrapped
    private ActivityDetail activityDetails;

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public ActivityDetail getActivityDetails() {
        return activityDetails;
    }

    public ActivityDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public ActivityDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public ActivityDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public ActivityDto setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ActivityDto setActivityType(ActivityType activityType) {
        this.activityType = activityType;
        return this;
    }

    public ActivityDto setActivityDetails(ActivityDetail activityDetails) {
        this.activityDetails = activityDetails;
        return this;
    }
}
