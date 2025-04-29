package ansk98.de.movemintserver.eventing.user;

import ansk98.de.movemintserver.eventing.IEvent;

import java.time.ZonedDateTime;

/**
 * Abstract user event.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public abstract class AbstractUserEvent implements IEvent {

    private final String identity;
    private final ZonedDateTime eventTime;

    public AbstractUserEvent(String identity) {
        this.identity = identity;
        this.eventTime = ZonedDateTime.now();
    }

    public String getIdentity() {
        return identity;
    }

    public ZonedDateTime getEventTime() {
        return eventTime;
    }
}
