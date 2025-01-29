package ansk98.de.movemintserver.coaching;

import java.util.List;

/**
 * Generic object for a constructed exercise / workout.
 *
 * @param exercises list of exercises
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record ExerciseRoutine(List<Exercise> exercises) {
    public record Exercise(String name, String guidelines) {

    }
}
