package ansk98.de.movemintserver.settings;

import ansk98.de.movemintserver.eventing.user.BeforeUserDeletedEvent;

/**
 * Service to manage the setting of a user.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public interface ISettingsService {

    void onBeforeUserDeleted(BeforeUserDeletedEvent beforeUserDeletedEvent);

    SettingsParams findSettingsForAuthenticatedUser();

    void defineSettings(SettingsParams settings);

    void updateSettings(SettingsParams settings);
}
