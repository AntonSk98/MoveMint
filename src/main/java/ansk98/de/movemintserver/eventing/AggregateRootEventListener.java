package ansk98.de.movemintserver.eventing;

import jakarta.annotation.PostConstruct;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

/**
 * Hibernate event listener that injects an {@link IEventPublisher} into entities extending {@link DomainEventAware} after they are loaded from the database.
 * This allows to publish events from aggregate roots instead of relying on the business layer.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Component
class AggregateRootEventListener implements PostLoadEventListener {

    private final IEventPublisher eventPublisher;
    private final SessionFactory sessionFactory;

    public AggregateRootEventListener(IEventPublisher eventPublisher,
                                      SessionFactory sessionFactory) {
        this.eventPublisher = eventPublisher;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Registers {@link AggregateRootEventListener}.
     */
    @PostConstruct
    public void registerListeners() {
        sessionFactory
                .unwrap(SessionFactoryImpl.class)
                .getServiceRegistry()
                .getService(EventListenerRegistry.class)
                .getEventListenerGroup(EventType.POST_LOAD)
                .appendListener(this);
    }

    /**
     * Injects {@link IEventPublisher} to an entity if it {@link DomainEventAware} one.
     *
     * @param event post load event
     */
    @Override
    public void onPostLoad(PostLoadEvent event) {
        if (event.getEntity() instanceof DomainEventAware domainEventAware) {
            domainEventAware.setEventPublisher(eventPublisher);
        }
    }
}
