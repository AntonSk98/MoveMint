package ansk98.de.movemintserver.activities.common;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to create | accept | reject an activity.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@RestController
@RequestMapping("/public/activities")
public class ActivityController {

    private final IActivityServiceDelegate activityService;

    public ActivityController(IActivityServiceDelegate activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/find")
    public List<ActivityDto> findActivities() {
        return activityService.findActivities();
    }

    @PostMapping("/accept")
    public void acceptActivity(@RequestBody AcceptActivityCommand acceptActivityCommand) {
        activityService.acceptActivity(acceptActivityCommand);
    }

    @PostMapping("/reject")
    public void rejectActivity(@RequestBody RejectActivityCommand rejectActivityCommand) {
        activityService.rejectActivity(rejectActivityCommand);
    }
}
