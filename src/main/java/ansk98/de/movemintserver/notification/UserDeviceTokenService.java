package ansk98.de.movemintserver.notification;

import ansk98.de.movemintserver.user.IUserService;
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
        UserDeviceToken userDeviceToken = UserDeviceToken.of(deviceTokenRequest.deviceToken(), userService.requireUser(Function.identity()));
        userDeviceTokenRepository.save(userDeviceToken);
    }
}
