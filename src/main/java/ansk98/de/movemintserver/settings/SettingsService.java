package ansk98.de.movemintserver.settings;

import ansk98.de.movemintserver.user.IUserService;
import ansk98.de.movemintserver.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

/**
 * Implementation of {@link ISettingsService}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class SettingsService implements ISettingsService {

    private final ISettingsRepository settingsRepository;
    private final IUserService userService;

    public SettingsService(ISettingsRepository settingsRepository, IUserService userService) {
        this.settingsRepository = settingsRepository;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public SettingsParams findSettingsForAuthenticatedUser() {
        User user = userService.requireUser(Function.identity());
        return map(settingsRepository.findByUser(user));
    }

    @Override
    @Transactional
    public void defineSettings(SettingsParams settings) {
        User user = userService.requireUser(Function.identity());
        settingsRepository.save(map(user, settings));
    }

    @Override
    @Transactional
    public void updateSettings(SettingsParams settings) {
        User user = userService.requireUser(Function.identity());
        Settings currentSettings = settingsRepository.findByUser(user);
        currentSettings.update(
                new ActivitySettings.Builder()
                        .enableWorkStandingNotification(settings.activitySettings().enableWorkStandingNotification())
                        .enableWaterIntakeNotification(settings.activitySettings().enableWaterIntakeNotification())
                        .enabledStretchingNotification(settings.activitySettings().enabledStretchingNotification())
                        .enableRestVisionNotification(settings.activitySettings().enableRestVisionNotification()),
                map(settings.notificationSettings())
        );
    }

    private SettingsParams map(Settings settings) {
        return new SettingsParams(
                new SettingsParams.ActivityDetails(
                        settings.getActivitySettings().isEnabledStretchingNotification(),
                        settings.getActivitySettings().isEnableRestVisionNotification(),
                        settings.getActivitySettings().isEnableWaterIntakeNotification(),
                        settings.getActivitySettings().isEnableWorkStandingNotification()
                ),
                settings.getNotificationSettings().stream().map(notificationPolicy -> new SettingsParams.NotificationEntryDetail(
                        notificationPolicy.getDayOfWeek(),
                        notificationPolicy.getFrom(),
                        notificationPolicy.getTo()
                )).toList()
        );
    }

    private Settings map(User user, SettingsParams settings) {
        List<NotificationPolicy> notificationPolicies = map(settings.notificationSettings());

        ActivitySettings.Builder activitySettings = new ActivitySettings.Builder()
                .enableRestVisionNotification(settings.activitySettings().enableRestVisionNotification())
                .enabledStretchingNotification(settings.activitySettings().enabledStretchingNotification())
                .enableWaterIntakeNotification(settings.activitySettings().enableWaterIntakeNotification())
                .enableWorkStandingNotification(settings.activitySettings().enableWorkStandingNotification());

        return Settings.create(user, activitySettings, notificationPolicies);
    }

    private List<NotificationPolicy> map(List<SettingsParams.NotificationEntryDetail> notificationEntryDetails) {
        return notificationEntryDetails.stream().map(notificationDetails -> NotificationPolicy.of(
                notificationDetails.dayOfWeek(),
                notificationDetails.from(),
                notificationDetails.to())
        ).toList();
    }
}
