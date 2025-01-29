package ansk98.de.movemintserver.notification;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to persist user device token.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@RestController
@RequestMapping("/public/device-token")
public class UserDeviceTokenController {

    private final IUserDeviceTokenService userDeviceTokenService;

    public UserDeviceTokenController(IUserDeviceTokenService userDeviceTokenService) {
        this.userDeviceTokenService = userDeviceTokenService;
    }

    @PostMapping
    void deviceToken(@RequestBody @Valid DeviceTokenRequest deviceTokenRequest) {
        userDeviceTokenService.saveUserDeviceToken(deviceTokenRequest);
    }
}
