package ansk98.de.movemintserver.auth;

import java.util.List;

/**
 * Encapsulates user claims of a token.
 *
 * @param username    username
 * @param authorities authorities
 * @author Anton Skripijn (anton.tech98@gmail.com)
 */
public record UserClaims(String username, List<String> authorities) {
}
