package ansk98.de.movemintserver.eventing;

import ansk98.de.movemintserver.eventing.activity.ActivityAcceptedEvent;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Service responsible for dispatching and handling application events asynchronously.
 * This service processes events of type {@code ActivityAcceptedEvent} in a resilient manner.
 * It supports asynchronous execution, automatic retries with exponential backoff, and transaction-aware event handling.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Component
public class EventDispatcherService {

    @Async
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 10000, multiplier = 2.0))
    @TransactionalEventListener
    public void handleEvent(ActivityAcceptedEvent event) {
        // asynchronous processing
    }
}
