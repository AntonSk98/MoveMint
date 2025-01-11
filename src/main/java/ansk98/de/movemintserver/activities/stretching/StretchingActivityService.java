package ansk98.de.movemintserver.activities.stretching;

import ansk98.de.movemintserver.activities.common.AcceptActivityCommand;
import ansk98.de.movemintserver.activities.common.ActivityDto;
import ansk98.de.movemintserver.activities.common.DeclineActivityCommand;
import ansk98.de.movemintserver.activities.common.IActivityService;
import ansk98.de.movemintserver.user.IUserService;
import ansk98.de.movemintserver.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

import static ansk98.de.movemintserver.activities.common.ActivityType.STRETCHING_ACTIVITY;
import static ansk98.de.movemintserver.activities.common.ValidationUtils.validateActivityType;

/**
 * Implementation of {@link IActivityService} for {@link StretchingActivity}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class StretchingActivityService implements IActivityService {

    private final IStretchingActivityRepository stretchingActivityRepository;
    private final IUserService userService;

    public StretchingActivityService(IStretchingActivityRepository stretchingActivityRepository,
                                     IUserService userService) {
        this.stretchingActivityRepository = stretchingActivityRepository;
        this.userService = userService;
    }

    @Override
    public List<ActivityDto> findActivities() {
        // todo ensure authenticated
        User user = userService.requireUser("", Function.identity());
        return null;
    }

    @Override
    public void acceptActivity(AcceptActivityCommand acceptCommand) {
        // todo ensure authenticated
        validateActivityType(STRETCHING_ACTIVITY, acceptCommand.activityType());
        // todo
    }

    @Override
    public void declineActivity(DeclineActivityCommand declineCommand) {
        // todo ensure authenticated
        validateActivityType(STRETCHING_ACTIVITY, declineCommand.activityType());
        // todo
    }
}
