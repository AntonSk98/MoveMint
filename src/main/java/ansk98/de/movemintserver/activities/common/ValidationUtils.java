package ansk98.de.movemintserver.activities.common;

import jakarta.annotation.Nonnull;

/**
 * Contains utility validation methods for activities.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public class ValidationUtils {

    public static void validateActivityType(@Nonnull ActivityType expected, @Nonnull ActivityType current) {
        if (expected.equals(current)) {
            return;
        }

        throw new IllegalStateException("Unsupported activity type. Expected: " + expected + ". But received: " + current);
    }
}
