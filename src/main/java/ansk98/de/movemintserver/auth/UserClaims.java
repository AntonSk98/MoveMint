package ansk98.de.movemintserver.auth;

import java.util.List;

/**
 * Encapsulates user claims of a token.
 *
 * @param identity    identity
 * @param authorities authorities
 * @author Anton Skripijn (anton.tech98@gmail.com)
 */
public record UserClaims(String identity, List<String> authorities) {
}
