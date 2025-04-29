package ansk98.de.movemintserver.eventing;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IEventPublisher}.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Component
class EventPublisher implements IEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    protected EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishEvent(IEvent activityEvent) {
        applicationEventPublisher.publishEvent(activityEvent);
    }
}
