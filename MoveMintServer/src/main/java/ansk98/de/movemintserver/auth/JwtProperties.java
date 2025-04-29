package ansk98.de.movemintserver.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

/**
 * Encapsulates configuration properties for JWT.
 *
 * @param issuer       token issuer
 * @param accessToken  access token properties
 * @param refreshToken refresh token properties
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@ConfigurationProperties(prefix = "authentication.jwt")
public record JwtProperties(
        String issuer,
        TokenProperties accessToken,
        TokenProperties refreshToken
) {

    public record TokenProperties(
            String key,
            Duration validity,
            List<String> roles
    ) {
    }
}
