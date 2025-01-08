package ansk98.de.movemintserver.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown related to token-related operations.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenException extends RuntimeException {

    public TokenException(String message) {
        super(message);
    }
}
