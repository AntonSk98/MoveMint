package ansk98.de.movemintserver.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that is triggered by unauthorized access.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnauthorizedAccessException extends RuntimeException {
}
