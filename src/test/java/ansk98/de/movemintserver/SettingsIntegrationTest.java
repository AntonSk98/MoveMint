package ansk98.de.movemintserver;

import ansk98.de.movemintserver.settings.ISettingsRepository;
import ansk98.de.movemintserver.settings.SettingsParams;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.emptyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SettingsIntegrationTest extends IntegrationTestSupport {

    private static final String TEST_USER = "testuser@gmail.com";

    private static final String DEFINE_SETTINGS_PATH = "/public/settings/define_settings";
    private static final String UPDATE_SETTINGS_PATH = "/public/settings/update_settings";
    private static final String FIND_SETTINGS_PATH = "/public/settings/find";

    private static final SettingsParams VALID_SETTINGS = new SettingsParams(
            new SettingsParams.ActivityDetails(
                    true,
                    true,
                    false,
                    false
            ),
            List.of(
                    new SettingsParams.NotificationEntryDetail(
                            DayOfWeek.MONDAY,
                            LocalTime.of(9, 0, 0),
                            LocalTime.of(17, 0, 0)
                    ),
                    new SettingsParams.NotificationEntryDetail(
                            DayOfWeek.TUESDAY,
                            LocalTime.of(8, 30, 0),
                            LocalTime.of(18, 0, 0)
                    ),
                    new SettingsParams.NotificationEntryDetail(
                            DayOfWeek.WEDNESDAY,
                            LocalTime.of(12, 0, 0),
                            LocalTime.of(18, 0, 0)
                    ),
                    new SettingsParams.NotificationEntryDetail(
                            DayOfWeek.FRIDAY,
                            LocalTime.of(8, 0, 0),
                            LocalTime.of(13, 30, 0)
                    )
            )
    );

    @Autowired
    private ISettingsRepository settingsRepository;

    @Test
    public void testNoSettingsExist() throws Exception {
        requestAs(TEST_USER, MockMvcRequestBuilders.get(FIND_SETTINGS_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(emptyString()));
    }

    @BeforeEach
    public void setup() {
        createUser(TEST_USER);
    }

    @AfterEach
    public void teardown() {
        deleteUser(TEST_USER);

        Assertions.assertTrue(settingsRepository.findAll().isEmpty());
    }

    @Test
    public void testInvalidSettingsValidation() throws Exception {
        SettingsParams settingsParams = new SettingsParams(null, emptyList());
        requestAs(TEST_USER, MockMvcRequestBuilders.post(DEFINE_SETTINGS_PATH).content(toJson(settingsParams)))
                .andExpect(status().isBadRequest());

        settingsParams = new SettingsParams(VALID_SETTINGS.activitySettings(), emptyList());
        requestAs(TEST_USER, MockMvcRequestBuilders.post(DEFINE_SETTINGS_PATH).content(toJson(settingsParams)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDefineSettingsSuccessfully() throws Exception {
        requestAs(TEST_USER, MockMvcRequestBuilders.post(DEFINE_SETTINGS_PATH).content(toJson(VALID_SETTINGS)))
                .andExpect(status().isOk());

        requestAs(TEST_USER, MockMvcRequestBuilders.get(FIND_SETTINGS_PATH))
                .andExpect(status().isOk())
                .andExpect(result -> validateSettingsPolicy(VALID_SETTINGS, toObject(result.getResponse().getContentAsString(), SettingsParams.class)));
    }

    @Test
    public void testAnonymousUserCannotSeeSettings() throws Exception {
        requestAnonymous(MockMvcRequestBuilders.get(FIND_SETTINGS_PATH))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdateSettings() throws Exception {
        SettingsParams updatedSettings = getUpdatedNotificationPolicy();

        requestAs(TEST_USER, MockMvcRequestBuilders.post(DEFINE_SETTINGS_PATH).content(toJson(VALID_SETTINGS)))
                .andExpect(status().isOk());

        requestAs(TEST_USER, MockMvcRequestBuilders.post(UPDATE_SETTINGS_PATH).content(toJson(updatedSettings)))
                .andExpect(status().isOk());

        requestAs(TEST_USER, MockMvcRequestBuilders.get(FIND_SETTINGS_PATH))
                .andExpect(result -> validateSettingsPolicy(updatedSettings, toObject(result.getResponse().getContentAsString(), SettingsParams.class)));
    }

    private void validateSettingsPolicy(SettingsParams expectedSettings, SettingsParams currentSettings) {
        Assertions.assertEquals(expectedSettings.activitySettings(), currentSettings.activitySettings());
        Assertions.assertTrue(CollectionUtils.isEqualCollection(expectedSettings.notificationSettings(), currentSettings.notificationSettings()));
    }

    private SettingsParams getUpdatedNotificationPolicy() {
        List<SettingsParams.NotificationEntryDetail> updatedNotificationPolicy = new ArrayList<>(VALID_SETTINGS.notificationSettings());
        updatedNotificationPolicy.add(new SettingsParams.NotificationEntryDetail(
                DayOfWeek.FRIDAY,
                LocalTime.of(6, 30, 0),
                LocalTime.of(8, 30, 0)
        ));

        return new SettingsParams(
                new SettingsParams.ActivityDetails(
                        false,
                        false,
                        false,
                        false
                ),
                updatedNotificationPolicy
        );
    }
}
