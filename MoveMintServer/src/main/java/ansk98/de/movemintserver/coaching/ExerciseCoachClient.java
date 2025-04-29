package ansk98.de.movemintserver.coaching;

import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IExerciseCoachClient}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class ExerciseCoachClient implements IExerciseCoachClient {

    @Override
    public ExerciseRoutine stretchingRoutine(PhysicalAttributes userPhysicalDetails) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
