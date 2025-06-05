package ansk98.de.movemintserver.notification;

import ansk98.de.movemintserver.user.IUserService;
import ansk98.de.movemintserver.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

/**
 * Implementation of {@link IUserDeviceTokenService}
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class UserDeviceTokenService implements IUserDeviceTokenService {

    private final IUserDeviceTokenRepository userDeviceTokenRepository;
    private final IUserService userService;

    public UserDeviceTokenService(IUserService userService, IUserDeviceTokenRepository userDeviceTokenRepository) {
        this.userDeviceTokenRepository = userDeviceTokenRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void saveUserDeviceToken(DeviceTokenRequest deviceTokenRequest) {
        User user = userService.requireUser(Function.identity());
        userDeviceTokenRepository.findByUserIdentity(user.getIdentity()).ifPresentOrElse(
                token -> token.updateToken(deviceTokenRequest.deviceToken()),
                () -> {
                    UserDeviceToken userDeviceToken = UserDeviceToken.of(deviceTokenRequest.deviceToken(), user);
                    userDeviceTokenRepository.save(userDeviceToken);
                }
        );
    }
}
