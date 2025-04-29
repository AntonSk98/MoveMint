package ansk98.de.movemintserver.activities.common;

import ansk98.de.movemintserver.eventing.DomainEventAware;
import ansk98.de.movemintserver.eventing.activity.ActivityAcceptedEvent;
import ansk98.de.movemintserver.eventing.activity.ActivityRejectedEvent;
import ansk98.de.movemintserver.user.User;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import java.time.ZonedDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

/**
 * Common properties shared by all {@link IActivity} entities.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@MappedSuperclass
public abstract class AbstractActivity extends DomainEventAware implements IActivity {
    @Id
    private UUID id;
    private ZonedDateTime createdAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public AbstractActivity(@Nonnull User user) {
        this.id = UUID.randomUUID();
        this.createdAt = ZonedDateTime.now();
        this.user = user;
    }

    /**
     * No-args.
     */
    protected AbstractActivity() {

    }

    public void accept() {
        publish(ActivityAcceptedEvent.of(this));
    }

    public void reject() {
        publish(ActivityRejectedEvent.of(this));
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getUserIdentity() {
        return user.getIdentity();
    }
}
