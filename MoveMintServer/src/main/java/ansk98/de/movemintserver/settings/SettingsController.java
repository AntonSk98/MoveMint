package ansk98.de.movemintserver.settings;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to manage settings of a currently authenticated user.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@RestController
@RequestMapping("/public/settings")
public class SettingsController {

    private final ISettingsService settingsService;

    public SettingsController(ISettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping("/find")
    public SettingsParams findSettings() {
        return settingsService.findSettingsForAuthenticatedUser();
    }

    @PostMapping("/update_settings")
    public void updateSettings(@Valid @RequestBody SettingsParams settings) {
        settingsService.updateSettings(settings);
    }

    @PostMapping("/define_settings")
    public void defineSettings(@Valid @RequestBody SettingsParams settings) {
        settingsService.defineSettings(settings);
    }
}
