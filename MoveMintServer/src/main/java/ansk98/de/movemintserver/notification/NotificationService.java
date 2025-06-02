package ansk98.de.movemintserver.notification;

import ansk98.de.movemintserver.activities.common.ActivityDto;
import ansk98.de.movemintserver.activities.common.ActivityType;
import ansk98.de.movemintserver.activities.common.IActivityCommand;
import ansk98.de.movemintserver.activities.common.IActivityServiceDelegate;
import ansk98.de.movemintserver.settings.ISettingsRepository;
import ansk98.de.movemintserver.settings.Settings;
import ansk98.de.movemintserver.user.IUserRepository;
import ansk98.de.movemintserver.user.NotificationUserProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Implementation of {@link INotificationService}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class NotificationService implements INotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);


    private final IUserDeviceTokenRepository userDeviceTokenRepository;
    private final IUserRepository userRepository;
    private final IActivityServiceDelegate activityService;
    private final ISettingsRepository settingsRepository;
    private final NotificationProperties notificationProperties;
    private final IActivityCommand activityCommand;
    private final IClientPushNotifier pushNotifier;

    public NotificationService(IUserDeviceTokenRepository userDeviceTokenRepository,
                               IUserRepository userRepository,
                               IActivityServiceDelegate activityService,
                               ISettingsRepository settingsRepository,
                               NotificationProperties notificationProperties,
                               IActivityCommand activityCommand,
                               @Autowired(required = false) IClientPushNotifier pushNotifier) {
        this.userDeviceTokenRepository = userDeviceTokenRepository;
        this.userRepository = userRepository;
        this.activityService = activityService;
        this.settingsRepository = settingsRepository;
        this.notificationProperties = notificationProperties;
        this.activityCommand = activityCommand;
        this.pushNotifier = pushNotifier;
    }


    @Override
    @Transactional
    public void sendStretchingActivityNotification() {
        sendAbstractActivityNotification(ActivityType.STRETCHING_ACTIVITY);
    }

    @Override
    @Transactional
    public void sendVisionRestActivity() {
        sendAbstractActivityNotification(ActivityType.VISION_REST_ACTIVITY);
    }

    @Override
    @Transactional
    public void sendWaterIntakeActivity() {
        sendAbstractActivityNotification(ActivityType.WATER_INTAKE_ACTIVITY);
    }

    @Override
    @Transactional
    public void sendWorkStandingActivity() {
        sendAbstractActivityNotification(ActivityType.WORK_STANDING_ACTIVITY);
    }

    private void sendAbstractActivityNotification(ActivityType activityType) {
        userRepository
                .streamUserDetails()
                .filter(user -> shouldNotifyUserAbout(activityType).test(user))
                .forEach(user -> {
                    UserDeviceToken userDeviceToken = userDeviceTokenRepository.findByUserIdentity(user.getIdentity());
                    activityCommand.createForUserIdentifiedBy(user.getIdentity(), activityType);
                    Optional.ofNullable(pushNotifier)
                            .ifPresentOrElse(
                                    notifier -> Optional.ofNullable(userDeviceToken).ifPresent(deviceToken ->  notifier.notifyClientBy(deviceToken, activityType)),
                                    () -> LOGGER.warn("No push notifier is configured! Push notification will not be sent to the client {}", user.getIdentity()));
                });
    }

    private Predicate<NotificationUserProjection> shouldNotifyUserAbout(ActivityType activityType) {
        return user -> {
            Settings settings = settingsRepository.findByUserIdentity(user.getIdentity());
            if (settings == null) {
                return false;
            }

            if (!settings.isNotificationEnabledFor(activityType)) {
                return false;
            }

            if (!settings.adheresToUserNotificationPolicy(user.timezoneAsZoneId())) {
                return false;
            }

            Optional<ActivityDto> latestActivity = activityService.findLatestActivity(user.getIdentity(), activityType);

            Duration passedDuration = latestActivity.isPresent()
                    ? Duration.between(latestActivity.get().getCreatedAt(), ZonedDateTime.now())
                    : Duration.between(user.getRegisteredAt(), ZonedDateTime.now());

            return shouldNotifyUserAboutActivity(passedDuration, notificationProperties.getActivityFrequency(activityType)
            );
        };
    }

    private boolean shouldNotifyUserAboutActivity(Duration passedDuration, Duration notificationPolicyDuration) {
        return passedDuration.compareTo(notificationPolicyDuration) >= 0;
    }
}
