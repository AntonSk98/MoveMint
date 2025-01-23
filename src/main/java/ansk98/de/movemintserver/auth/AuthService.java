package ansk98.de.movemintserver.auth;

import ansk98.de.movemintserver.user.IUserService;
import ansk98.de.movemintserver.user.UserDto;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Implementation of {@link IAuthService}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Service
public class AuthService implements IAuthService {

    private final IUserService userService;
    private final JwtManager jwtManager;
    private final AuthenticationManager authenticationManager;

    public AuthService(IUserService userService,
                       JwtManager jwtManager,
                       AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtManager = jwtManager;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationDto login(AuthenticateUserCommand authenticateUserCommand) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticateUserCommand.identity(),
                        authenticateUserCommand.password()
                )
        );

        return new AuthenticationDto(
                jwtManager.generateAccessToken(authentication.getName()),
                jwtManager.generateRefreshToken(authentication.getName())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationDto issueNewTokens(@Valid RefreshTokenCommand refreshTokenCommand) {
        UserClaims userClaims = jwtManager.refreshTokenClaims(refreshTokenCommand.refreshToken());

        UserDto user = userService.findUserBy(userClaims.identity());

        Objects.requireNonNull(user);

        return new AuthenticationDto(
                jwtManager.generateAccessToken(user.identity()),
                jwtManager.generateRefreshToken(user.identity())
        );
    }

    @Override
    @Transactional
    public void registerUser(RegisterUserCommand registerUserCommand) {
        userService.createUser(registerUserCommand);
    }
}
