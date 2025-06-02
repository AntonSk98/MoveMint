package ansk98.de.movemintserver;

import ansk98.de.movemintserver.activities.common.ActivityDto;
import ansk98.de.movemintserver.activities.common.ActivityType;
import ansk98.de.movemintserver.activities.common.IActivityServiceDelegate;
import ansk98.de.movemintserver.coaching.ExerciseRoutine;
import ansk98.de.movemintserver.coaching.IExerciseCoachClient;
import ansk98.de.movemintserver.notification.IActivityNotificationScheduler;
import ansk98.de.movemintserver.notification.IClientPushNotifier;
import ansk98.de.movemintserver.settings.SettingsParams;
import ansk98.de.movemintserver.user.IUserRepository;
import ansk98.de.movemintserver.user.NotificationUserProjection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationIntegrationTest extends IntegrationTestSupport {

    private static final String TEST_USER_ONE = "test_user_one@gmail.com";
    private static final String TEST_USER_TWO = "test_user_two@gmail.com";

    @Autowired
    private IActivityNotificationScheduler activityNotificationScheduler;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @MockitoSpyBean
    private IUserRepository userRepository;
    @MockitoSpyBean
    private IActivityServiceDelegate activityService;

    @MockitoBean
    private IClientPushNotifier clientPushNotifier;
    @MockitoBean
    private IExerciseCoachClient exerciseCoachClient;

    @BeforeEach
    public void setUp() {
        createUser(TEST_USER_ONE);
        createUser(TEST_USER_TWO);

        when(exerciseCoachClient.stretchingRoutine(any())).thenReturn(new ExerciseRoutine(Collections.emptyList()));
    }

    @AfterEach
    public void tearDown() {
        deleteUser(TEST_USER_ONE);
        deleteUser(TEST_USER_TWO);
    }

    @Test
    public void noNotificationIfDisabledTest() {
        SettingsParams.ActivityDetails activityDetailsNotification = new SettingsParams.ActivityDetails(
                false,
                false,
                false,
                false
        );


        SettingsParams settingsParams = new SettingsParams(
                activityDetailsNotification,
                List.of(
                        new SettingsParams.NotificationEntryDetail(
                                getTodayDayOfWeek(),
                                LocalTime.now().minusHours(8),
                                LocalTime.now().minusHours(1)
                        )
                )
        );

        defineSettings(TEST_USER_ONE, settingsParams);
        activityNotificationScheduler.sendStretchingActivityNotification();
        verify(clientPushNotifier, never()).notifyClientBy(any(), any());
    }

    @Test
    public void noNotificationIfNoPolicyForThatDayTest() {
        SettingsParams.ActivityDetails activityDetailsNotification = new SettingsParams.ActivityDetails(
                true,
                true,
                true,
                true
        );

        SettingsParams settingsParams = new SettingsParams(
                activityDetailsNotification,
                List.of(
                        new SettingsParams.NotificationEntryDetail(
                                getTodayDayOfWeek().plus(1),
                                LocalTime.now().minusHours(8),
                                LocalTime.now().minusHours(1)
                        )
                )
        );
        defineSettings(TEST_USER_ONE, settingsParams);
        activityNotificationScheduler.sendStretchingActivityNotification();
        verify(clientPushNotifier, never()).notifyClientBy(any(), any());
    }

    @Test
    public void noNotificationIfOutOfDesiredTimeTest() {
        SettingsParams.ActivityDetails activityDetailsNotification = new SettingsParams.ActivityDetails(
                true,
                true,
                true,
                true
        );

        SettingsParams settingsParams = new SettingsParams(
                activityDetailsNotification,
                List.of(
                        new SettingsParams.NotificationEntryDetail(
                                getTodayDayOfWeek(),
                                LocalTime.now().minusHours(8),
                                LocalTime.now().minusHours(2)
                        )
                )
        );
        defineSettings(TEST_USER_ONE, settingsParams);
        activityNotificationScheduler.sendStretchingActivityNotification();
        verify(clientPushNotifier, never()).notifyClientBy(any(), any());
    }

    @Test
    public void notificationActivityTest() {

        transactionTemplate.executeWithoutResult(__ ->
                when(userRepository.streamUserDetails()).thenReturn(Stream.of(
                        new NotificationUserProjection() {
                            @Override
                            public String getIdentity() {
                                return TEST_USER_ONE;
                            }

                            @Override
                            public String getTimezone() {
                                return ZoneId.systemDefault().toString();
                            }

                            @Override
                            public ZonedDateTime getRegisteredAt() {
                                return ZonedDateTime.now().minusHours(3);
                            }
                        }
                ))
        );

        SettingsParams.ActivityDetails activityDetailsNotification = new SettingsParams.ActivityDetails(
                true,
                true,
                true,
                true
        );

        SettingsParams settingsParams = new SettingsParams(
                activityDetailsNotification,
                List.of(
                        new SettingsParams.NotificationEntryDetail(
                                getTodayDayOfWeek(),
                                LocalTime.now().minusHours(2),
                                LocalTime.now().plusHours(2)
                        )
                )
        );

        defineSettings(TEST_USER_ONE, settingsParams);
        activityNotificationScheduler.sendStretchingActivityNotification();
        verify(clientPushNotifier, times(1)).notifyClientBy(any(), eq(ActivityType.STRETCHING_ACTIVITY));
        Mockito.reset(userRepository);


        // check with activity
        when(activityService.findLatestActivity(TEST_USER_ONE, ActivityType.STRETCHING_ACTIVITY)).
                thenReturn(
                        Optional.of(
                                new ActivityDto().setId(UUID.randomUUID()).setCreatedAt(ZonedDateTime.now().minusHours(3))
                        )
                );
        activityNotificationScheduler.sendStretchingActivityNotification();
        verify(clientPushNotifier, times(2)).notifyClientBy(any(), eq(ActivityType.STRETCHING_ACTIVITY));
        Mockito.reset(activityService);
    }

    private void defineSettings(String identity, SettingsParams settingsParams) {
        try {
            requestAs(identity, MockMvcRequestBuilders.post(DEFINE_SETTINGS_PATH).content(toJson(settingsParams)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static DayOfWeek getTodayDayOfWeek() {
        return ZonedDateTime.now().getDayOfWeek();
    }
}
