package ansk98.de.movemintserver.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Encapsulated utility methods to obtain information about the authenticated user.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public class AuthenticationUtils {

    /**
     * Returns the identity of the currently authenticated user.
     *
     * @return user identity
     */
    public static String currentUserIdentity() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Returns all granted authorities of the authenticated user.
     *
     * @return granted authorities
     */
    public static List<String> currentUserAuthorities() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
    }
}
