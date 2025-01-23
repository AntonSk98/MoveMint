package ansk98.de.movemintserver.eventing;

/**
 * Publisher that is responsible for dispatching events.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface IEventPublisher {
    void publishEvent(IActivityEvent activityEvent);
}
