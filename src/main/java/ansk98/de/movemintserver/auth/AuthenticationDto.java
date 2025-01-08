package ansk98.de.movemintserver.auth;

/**
 * Encapsulates authentication tokens.
 *
 * @param accessToken  token to access the API
 * @param refreshToken token to request a new access token
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record AuthenticationDto(Token accessToken, Token refreshToken) {
}
