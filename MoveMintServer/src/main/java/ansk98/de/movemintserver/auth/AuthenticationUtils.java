package ansk98.de.movemintserver.auth;

import ansk98.de.movemintserver.user.UnauthorizedAccessException;
import jakarta.annotation.Nonnull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
    public static String requireUserIdentity() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName)
                .orElseThrow();
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

    /**
     * Checks that the authenticated user is allowed to perform an action on behalf of the user of the passed identity.
     *
     * @param identity user identity
     */
    public static void ensureCanActOnBehalfOf(@Nonnull String identity) {
        if (identity.equals(requireUserIdentity())) {
            return;
        }

        throw new UnauthorizedAccessException();
    }
}
