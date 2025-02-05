package ansk98.de.movemintserver;

import ansk98.de.movemintserver.activities.common.*;
import ansk98.de.movemintserver.activities.stretching.StretchingActivity;
import ansk98.de.movemintserver.activities.vision.VisionRestActivity;
import ansk98.de.movemintserver.activities.waterintake.WaterIntakeActivity;
import ansk98.de.movemintserver.activities.workstanding.WorkStandingActivity;
import ansk98.de.movemintserver.coaching.ExerciseCoachClient;
import ansk98.de.movemintserver.coaching.ExerciseRoutine;
import ansk98.de.movemintserver.user.IUserRepository;
import ansk98.de.movemintserver.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ActivityIntegrationTest extends IntegrationTestSupport {

    private static final String FIND_ACTIVITY_PATH = "/public/activities/find";
    private static final String ACCEPT_ACTIVITY_PATH = "/public/activities/accept";
    private static final String REJECT_ACTIVITY_PATH = "/public/activities/reject";

    private static final String TEST_USER_ONE = "test_user_one@gmail.com";
    private static final String TEST_USER_TWO = "test_user_two@gmail.com";

    @Autowired
    private IActivityServiceDelegate activityService;
    @Autowired
    private IUserRepository userRepository;
    @MockitoBean
    private ExerciseCoachClient exerciseCoachClient;

    @BeforeEach
    public void setUp() {
        createUser(TEST_USER_ONE);
        createUser(TEST_USER_TWO);

        Mockito.when(exerciseCoachClient.stretchingRoutine(Mockito.any())).thenReturn(
                new ExerciseRoutine(
                        List.of(
                                new ExerciseRoutine.Exercise("Jumping Jack", "Bla-bla"),
                                new ExerciseRoutine.Exercise("Mountain climbers", "Bla-bla")
                        )
                )
        );
    }

    @AfterEach
    public void tearDown() {
        deleteUser(TEST_USER_ONE);
        deleteUser(TEST_USER_TWO);
    }

    @Test
    public void findActivityIntegrationTest() throws Exception {
        createActivityFor(TEST_USER_ONE, ActivityType.STRETCHING_ACTIVITY);
        createActivityFor(TEST_USER_ONE, ActivityType.WATER_INTAKE_ACTIVITY);
        createActivityFor(TEST_USER_ONE, ActivityType.STRETCHING_ACTIVITY);

        createActivityFor(TEST_USER_TWO, ActivityType.WATER_INTAKE_ACTIVITY);
        createActivityFor(TEST_USER_TWO, ActivityType.WATER_INTAKE_ACTIVITY);
        createActivityFor(TEST_USER_TWO, ActivityType.WORK_STANDING_ACTIVITY);
        createActivityFor(TEST_USER_TWO, ActivityType.WORK_STANDING_ACTIVITY);
        createActivityFor(TEST_USER_TWO, ActivityType.WORK_STANDING_ACTIVITY);

        MvcResult result = requestAs(TEST_USER_ONE, MockMvcRequestBuilders.get(FIND_ACTIVITY_PATH))
                .andExpect(status().isOk())
                .andReturn();
        List<ActivityDto> activityListForFirstUser = getActivities(result);
        Assertions.assertEquals(3, activityListForFirstUser.size());
        Assertions.assertEquals(2, activityListForFirstUser
                .stream()
                .filter(activityDto -> activityDto.getActivityType().equals(ActivityType.STRETCHING_ACTIVITY))
                .count());
        Assertions.assertEquals(1, activityListForFirstUser
                .stream()
                .filter(activityDto -> activityDto.getActivityType().equals(ActivityType.WATER_INTAKE_ACTIVITY))
                .count());

        MvcResult secondResponse = requestAs(TEST_USER_TWO, MockMvcRequestBuilders.get(FIND_ACTIVITY_PATH))
                .andExpect(status().isOk())
                .andReturn();
        List<ActivityDto> activityListForSecondUser = getActivities(secondResponse);
        Assertions.assertEquals(5, activityListForSecondUser.size());
        Assertions.assertEquals(2, activityListForSecondUser
                .stream()
                .filter(activityDto -> activityDto.getActivityType().equals(ActivityType.WATER_INTAKE_ACTIVITY))
                .count());
        Assertions.assertEquals(3, activityListForSecondUser
                .stream()
                .filter(activityDto -> activityDto.getActivityType().equals(ActivityType.WORK_STANDING_ACTIVITY))
                .count());

        requestAnonymous(MockMvcRequestBuilders.get(FIND_ACTIVITY_PATH))
                .andExpect(status().isForbidden());
    }

    @Test
    public void acceptActivityTest() throws Exception {
        testAbstractActivityFlow(ACCEPT_ACTIVITY_PATH);
    }

    @Test
    public void rejectActivityTest() throws Exception {
        testAbstractActivityFlow(REJECT_ACTIVITY_PATH);
    }

    @Test
    public void findLatestActivityTest() {
        createActivityFor(TEST_USER_ONE, ActivityType.STRETCHING_ACTIVITY);
        createActivityFor(TEST_USER_ONE, ActivityType.WATER_INTAKE_ACTIVITY);

        createActivityFor(TEST_USER_TWO, ActivityType.WATER_INTAKE_ACTIVITY);

        Assertions.assertTrue(activityService.findLatestActivity(TEST_USER_ONE, ActivityType.STRETCHING_ACTIVITY).isPresent());
        Assertions.assertTrue(activityService.findLatestActivity(TEST_USER_ONE, ActivityType.WATER_INTAKE_ACTIVITY).isPresent());
        Assertions.assertTrue(activityService.findLatestActivity(TEST_USER_ONE, ActivityType.WORK_STANDING_ACTIVITY).isEmpty());
        Assertions.assertTrue(activityService.findLatestActivity(TEST_USER_ONE, ActivityType.VISION_REST_ACTIVITY).isEmpty());

        Assertions.assertTrue(activityService.findLatestActivity(TEST_USER_TWO, ActivityType.WATER_INTAKE_ACTIVITY).isPresent());
        Assertions.assertTrue(activityService.findLatestActivity(TEST_USER_TWO, ActivityType.STRETCHING_ACTIVITY).isEmpty());
        Assertions.assertTrue(activityService.findLatestActivity(TEST_USER_TWO, ActivityType.WORK_STANDING_ACTIVITY).isEmpty());
        Assertions.assertTrue(activityService.findLatestActivity(TEST_USER_TWO, ActivityType.VISION_REST_ACTIVITY).isEmpty());

    }

    private void createActivityFor(String identity, ActivityType activityType) {
        User user = userRepository.findByIdentity(identity).orElseThrow();
        IActivity activity = switch (activityType) {
            case STRETCHING_ACTIVITY -> StretchingActivity.define(user);
            case VISION_REST_ACTIVITY -> VisionRestActivity.createFor(user);
            case WORK_STANDING_ACTIVITY -> WorkStandingActivity.createFor(user);
            case WATER_INTAKE_ACTIVITY -> WaterIntakeActivity.createFor(user);
        };

        activityService.createActivity(new CreateActivityCommand(activity));
    }

    private List<ActivityDto> getActivities(MvcResult result) {
        try {
            return objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void testAbstractActivityFlow(String path) throws Exception {
        createActivityFor(TEST_USER_ONE, ActivityType.STRETCHING_ACTIVITY);
        ActivityDto createdActivity = getActivities(requestAs(TEST_USER_ONE, MockMvcRequestBuilders.get(FIND_ACTIVITY_PATH)).andReturn()).getFirst();

        // user tries to reject an activity that does not belong to them
        requestAs(TEST_USER_TWO, MockMvcRequestBuilders.post(path)
                .content(toJson(new AcceptActivityCommand(createdActivity.getId(), ActivityType.STRETCHING_ACTIVITY))))
                .andExpect(status().isBadRequest());

        // user tries to reject an activity of the wrong non-existing type
        requestAs(TEST_USER_ONE, MockMvcRequestBuilders.post(path)
                .content(toJson(new AcceptActivityCommand(createdActivity.getId(), ActivityType.WATER_INTAKE_ACTIVITY))))
                .andExpect(status().isBadRequest());

        // rejecting activity
        requestAs(TEST_USER_ONE, MockMvcRequestBuilders.post(path)
                .content(toJson(new AcceptActivityCommand(createdActivity.getId(), ActivityType.STRETCHING_ACTIVITY))))
                .andExpect(status().isOk());

        // assert that accepted activity is deleted
        requestAs(TEST_USER_ONE, MockMvcRequestBuilders.get(FIND_ACTIVITY_PATH))
                .andExpect(jsonPath("$").isEmpty());
    }
}
