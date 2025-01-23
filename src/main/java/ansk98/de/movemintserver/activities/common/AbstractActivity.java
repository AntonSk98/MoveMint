package ansk98.de.movemintserver.activities.common;

import ansk98.de.movemintserver.eventing.activity.ActivityAcceptedEvent;
import ansk98.de.movemintserver.eventing.activity.ActivityRejectedEvent;
import ansk98.de.movemintserver.user.User;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.OnDelete;

import java.time.ZonedDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

/**
 * Common properties shared by all {@link IActivity} entities.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@MappedSuperclass
public abstract class AbstractActivity implements IActivity {
    @Id
    private UUID id;
    private ZonedDateTime createdAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = CASCADE)
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

    public ActivityAcceptedEvent accept() {
        return ActivityAcceptedEvent.of(this);
    }

    public ActivityRejectedEvent reject() {
        return ActivityRejectedEvent.of(this);
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
