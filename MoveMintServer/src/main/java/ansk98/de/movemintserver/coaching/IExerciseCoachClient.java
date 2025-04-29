package ansk98.de.movemintserver.coaching;


import ansk98.de.movemintserver.coaching.ExerciseRoutine;
import ansk98.de.movemintserver.coaching.PhysicalAttributes;

/**
 * Service that constructs various exercises.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IExerciseCoachClient {

    ExerciseRoutine stretchingRoutine(PhysicalAttributes userPhysicalDetails);
}
