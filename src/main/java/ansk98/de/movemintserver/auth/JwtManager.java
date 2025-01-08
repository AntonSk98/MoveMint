package ansk98.de.movemintserver.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Component to provide helper method to manage a JWT token.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Component
public class JwtManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtManager.class);

    private static final String AUTHORITIES_CLAIM = "authorities";

    private final JwtProperties jwtProperties;

    public JwtManager(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * Generates a new token to access the resources.
     *
     * @param subject client identity
     * @return token
     */
    public Token generateAccessToken(String subject) {
        Instant now = Instant.now();

        Key signingKey = Keys.hmacShaKeyFor(jwtProperties.accessToken().key().getBytes());

        String token = Jwts.builder()
                .issuer(jwtProperties.issuer())
                .subject(subject)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(jwtProperties.accessToken().validity())))
                .signWith(signingKey)
                .claims(Map.of(
                        "authorities", jwtProperties.accessToken().roles()
                ))
                .compact();

        return new Token(token);
    }

    /**
     * Generates a token that allows to require a new access token.
     *
     * @param subject client identity
     * @return token
     */
    public Token generateRefreshToken(String subject) {
        Instant now = Instant.now();
        String token = Jwts.builder()
                .issuer(jwtProperties.issuer())
                .subject(subject)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(jwtProperties.refreshToken().validity())))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.refreshToken().key().getBytes()))
                .claims(Map.of(
                        AUTHORITIES_CLAIM, jwtProperties.refreshToken().roles()
                ))
                .compact();

        return new Token(token);
    }

    /**
     * Extracts user claims from the access token.
     *
     * @param token access token
     * @return user claims
     */
    public UserClaims accessTokenClaims(String token) {
        Claims claims = toClaims(token, jwtProperties.accessToken().key());
        return new UserClaims(
                claims.getSubject(),
                claims.get("authorities", List.class)
        );
    }

    /**
     * Extracts user claims from the refresh token.
     *
     * @param token refresh token
     * @return user claims
     */
    public UserClaims refreshTokenClaims(String token) {
        Claims claims = toClaims(token, jwtProperties.refreshToken().key());
        return new UserClaims(
                claims.getSubject(),
                claims.get("authorities", List.class)
        );
    }


    private static Claims toClaims(String token, String key) {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(key.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            LOGGER.error("Invalid token. Exception: {}", e.getMessage());
            throw new TokenException("Invalid token");
        }
    }
}
