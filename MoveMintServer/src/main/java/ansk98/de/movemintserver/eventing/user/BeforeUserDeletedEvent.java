package ansk98.de.movemintserver.eventing.user;

import ansk98.de.movemintserver.eventing.user.AbstractUserEvent;

/**
 * Event published before user is deleted.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public class BeforeUserDeletedEvent extends AbstractUserEvent {

    public BeforeUserDeletedEvent(String identity) {
        super(identity);
    }
}
