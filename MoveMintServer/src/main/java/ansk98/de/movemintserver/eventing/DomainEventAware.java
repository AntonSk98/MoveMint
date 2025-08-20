package ansk98.de.movemintserver.eventing;

import java.util.Objects;

import jakarta.persistence.Transient;

/**
 * Adds support for domain entities to publish domain events.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 * @see IEvent
 * @see IEventPublisher
 */
public abstract class DomainEventAware {

    @Transient
    private IEventPublisher eventPublisher;

    public void publish(IEvent event) {
        Objects.requireNonNull(eventPublisher);
        eventPublisher.publishEvent(event);
    }

    void setEventPublisher(IEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
