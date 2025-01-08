package ansk98.de.movemintserver.auth;

/**
 * Authentication service.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IAuthService {

    /**
     * Authenticates a user
     *
     * @param authenticateUserCommand See {@link AuthenticateUserCommand}
     * @return {@link AuthenticationDto}
     */
    AuthenticationDto login(AuthenticateUserCommand authenticateUserCommand);

    /**
     * Issues a pair of new tokens
     *
     * @param refreshTokenCommand See {@link RefreshTokenCommand}
     * @return {@link AuthenticationDto}
     */
    AuthenticationDto issueNewTokens(RefreshTokenCommand refreshTokenCommand);

    /**
     * Creates a new user.
     *
     * @param registerUserCommand See {@link RegisterUserCommand}
     */
    void registerUser(RegisterUserCommand registerUserCommand);
}
