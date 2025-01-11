package ansk98.de.movemintserver.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception if the given identity is already taken.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdentityTakenException extends RuntimeException {

    public IdentityTakenException(String message) {
        super(message);
    }
}
