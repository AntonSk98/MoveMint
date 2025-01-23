package ansk98.de.movemintserver.auth;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that provides API related to authentication.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user.
     *
     * @param registerUserCommand See {@link RegisterUserCommand}
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterUserCommand registerUserCommand) {
        authService.registerUser(registerUserCommand);
        return ResponseEntity.noContent().build();
    }

    /**
     * Authenticates a user.
     *
     * @param authenticateUserCommand See {@link AuthenticateUserCommand}
     * @return {@link AuthenticationDto}
     */
    @PostMapping("/login")
    public AuthenticationDto loginUser(@RequestBody @Valid AuthenticateUserCommand authenticateUserCommand) {
        return authService.login(authenticateUserCommand);
    }

    /**
     * Endpoint to refresh a token.
     *
     * @param refreshTokenCommand See {@link RefreshTokenCommand}
     * @return {@link AuthenticationDto}
     */
    @PostMapping("/refresh-token")
    public AuthenticationDto refreshToken(@RequestBody @Valid RefreshTokenCommand refreshTokenCommand) {
        return authService.issueNewTokens(refreshTokenCommand);
    }
}
