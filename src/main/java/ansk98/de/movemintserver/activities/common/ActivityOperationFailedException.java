package ansk98.de.movemintserver.activities.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception during activity workflow.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActivityOperationFailedException extends RuntimeException {
}
