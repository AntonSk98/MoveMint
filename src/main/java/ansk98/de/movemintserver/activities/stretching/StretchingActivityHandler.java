package ansk98.de.movemintserver.activities.stretching;

import ansk98.de.movemintserver.activities.common.*;
import ansk98.de.movemintserver.coaching.ExerciseRoutine;
import ansk98.de.movemintserver.coaching.IExerciseCoachClient;
import ansk98.de.movemintserver.coaching.PhysicalAttributes;
import ansk98.de.movemintserver.eventing.IEventPublisher;
import ansk98.de.movemintserver.user.IUserService;
import ansk98.de.movemintserver.user.UserDto;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

import static ansk98.de.movemintserver.activities.common.ActivityType.STRETCHING_ACTIVITY;

/**
 * Implementation of {@link IActivityService} for {@link StretchingActivity}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class StretchingActivityHandler extends AbstractActivityHandler<StretchingActivity> {

    private final IExerciseCoachClient exerciseCoachClient;


    public StretchingActivityHandler(IActivityRepository<StretchingActivity> stretchingActivityRepository,
                                     IUserService userService,
                                     ActivitiesMetadata activitiesMetadata,
                                     IEventPublisher eventPublisher, IExerciseCoachClient exerciseCoachClient) {
        super(stretchingActivityRepository, userService, activitiesMetadata, eventPublisher);
        this.exerciseCoachClient = exerciseCoachClient;
    }

    @Override
    public void createActivity(CreateActivityCommand createActivityCommand) {
        StretchingActivity stretchingActivity = createActivityCommand.activity().map(StretchingActivity.class);
        UserDto userDetails = userService.findUserBy(createActivityCommand.activity().getUserIdentity());

        ExerciseRoutine exerciseRoutine = exerciseCoachClient.stretchingRoutine(
                new PhysicalAttributes(
                        userDetails.userDetailsParams().dateOfBirth().format(DateTimeFormatter.ISO_DATE),
                        userDetails.userDetailsParams().height(),
                        userDetails.userDetailsParams().weight(),
                        userDetails.userDetailsParams().gender().name()
                )
        );


        List<Exercise> exercises = exerciseRoutine.exercises()
                .stream()
                .map(exercise -> Exercise.create(exercise.name(), exercise.guidelines()))
                .toList();

        stretchingActivity.withExercises(exercises);

        super.createActivity(createActivityCommand);
    }

    @Override
    public StretchingActivity mapToActivity(CreateActivityCommand activityCommand) {
        return activityCommand.activity().map(StretchingActivity.class);
    }


    @Override
    public Function<StretchingActivity, ActivityDto> mapToActivityDto() {
        return activity -> new ActivityDto()
                .setId(activity.getId())
                .setTitle(activitiesMetadata.findByType(activity.getActivityType()).title())
                .setDescription(activitiesMetadata.findByType(activity.getActivityType()).description())
                .setActivityType(STRETCHING_ACTIVITY)
                .setCreatedAt(activity.getCreatedAt())
                .setActivityDetails(new ActivityDetail().addDetail("exercises", activity.getExercises()));
    }

    @Override
    public ActivityType getActivityType() {
        return STRETCHING_ACTIVITY;
    }
}
