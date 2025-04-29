package ansk98.de.movemintserver.notification;

import ansk98.de.movemintserver.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

/**
 * Entity that represents a unique device token of a client.
 * This device token is used to notify client about activities that should be performed.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Entity
public class UserDeviceToken {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String deviceToken;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    /**
     * No-args.
     */
    protected UserDeviceToken() {

    }

    private UserDeviceToken(String deviceToken, User user) {
        this.id = UUID.randomUUID();
        this.deviceToken = deviceToken;
        this.user = user;
    }

    public static UserDeviceToken of(String deviceToken, User user) {
        return new UserDeviceToken(deviceToken, user);
    }

    public String getDeviceToken() {
        return deviceToken;
    }
}
