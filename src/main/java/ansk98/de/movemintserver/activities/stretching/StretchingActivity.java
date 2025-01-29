package ansk98.de.movemintserver.activities.stretching;

import ansk98.de.movemintserver.activities.common.AbstractActivity;
import ansk98.de.movemintserver.activities.common.ActivityType;
import ansk98.de.movemintserver.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static ansk98.de.movemintserver.activities.common.ActivityType.STRETCHING_ACTIVITY;

/**
 * Activity to make a break and make some stretching.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Entity
public class StretchingActivity extends AbstractActivity {

    @JoinColumn(name = "stretching_activity")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Exercise> exercises;

    /**
     * No-args.
     */
    protected StretchingActivity() {
    }

    private StretchingActivity(User user) {
        super(user);
        this.exercises = Collections.emptyList();
    }

    public static StretchingActivity define(User user) {
        return new StretchingActivity(user);
    }

    public StretchingActivity withExercises(@NotNull List<Exercise> exercises) {
        Objects.requireNonNull(exercises);
        this.exercises = exercises;
        return this;
    }

    public List<Exercise> getExercises() {
        return Collections.unmodifiableList(exercises);
    }

    @Override
    public ActivityType getActivityType() {
        return STRETCHING_ACTIVITY;
    }
}
