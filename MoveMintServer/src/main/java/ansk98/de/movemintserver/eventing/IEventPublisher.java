package ansk98.de.movemintserver.eventing;

/**
 * Publisher that is responsible for dispatching events.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
interface IEventPublisher {
    void publishEvent(IEvent activityEvent);
}
