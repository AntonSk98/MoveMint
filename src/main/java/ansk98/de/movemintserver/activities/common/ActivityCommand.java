package ansk98.de.movemintserver.activities.common;

import ansk98.de.movemintserver.activities.stretching.StretchingActivity;
import ansk98.de.movemintserver.activities.vision.VisionRestActivity;
import ansk98.de.movemintserver.activities.waterintake.WaterIntakeActivity;
import ansk98.de.movemintserver.activities.workstanding.WorkStandingActivity;
import ansk98.de.movemintserver.user.IUserRepository;
import ansk98.de.movemintserver.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

/**
 * Implementation of {@link IActivityCommand}.
 *
 * @author Anton Skripin
 */
@Service
public class ActivityCommand implements IActivityCommand {

    private final IActivityServiceDelegate activityServiceDelegate;
    private final IUserRepository userRepository;

    public ActivityCommand(IActivityServiceDelegate activityServiceDelegate,
                           IUserRepository userRepository) {
        this.activityServiceDelegate = activityServiceDelegate;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createForUserIdentifiedBy(String identity, ActivityType activityType) {
        Supplier<User> findUserByIdentity = () -> userRepository.findByIdentity(identity).orElseThrow();

        switch (activityType) {
            case STRETCHING_ACTIVITY -> createStretchingExerciseFor(findUserByIdentity.get());
            case VISION_REST_ACTIVITY -> createVisionRestExerciseFor(findUserByIdentity.get());
            case WATER_INTAKE_ACTIVITY -> createWaterIntakeActivity(findUserByIdentity.get());
            case WORK_STANDING_ACTIVITY -> createWorkStandingActivity(findUserByIdentity.get());
            default -> throw new IllegalStateException("Unexpected activity: " + activityType);
        }
    }

    private void createWorkStandingActivity(User user) {
        activityServiceDelegate.createActivity(new CreateActivityCommand(WorkStandingActivity.createFor(user)));
    }

    private void createWaterIntakeActivity(User user) {
        activityServiceDelegate.createActivity(new CreateActivityCommand(WaterIntakeActivity.createFor(user)));
    }

    private void createStretchingExerciseFor(User user) {
        activityServiceDelegate.createActivity(new CreateActivityCommand(StretchingActivity.define(user)));
    }

    private void createVisionRestExerciseFor(User user) {
        activityServiceDelegate.createActivity(new CreateActivityCommand(VisionRestActivity.createFor(user)));
    }
}
