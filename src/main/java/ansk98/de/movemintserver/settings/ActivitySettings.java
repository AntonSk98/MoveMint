package ansk98.de.movemintserver.settings;

import jakarta.persistence.Embeddable;

/**
 * Settings related to activity notifications.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Embeddable
public class ActivitySettings {

    private boolean enabledStretchingNotification;
    private boolean enableRestVisionNotification;
    private boolean enableWaterIntakeNotification;
    private boolean enableWorkStandingNotification;

    protected ActivitySettings() {

    }

    private ActivitySettings(Builder builder) {
        this.enabledStretchingNotification = builder.enabledStretchingNotification;
        this.enableRestVisionNotification = builder.enableRestVisionNotification;
        this.enableWaterIntakeNotification = builder.enableWaterIntakeNotification;
        this.enableWorkStandingNotification = builder.enableWorkStandingNotification;
    }

    public boolean isEnabledStretchingNotification() {
        return enabledStretchingNotification;
    }

    public boolean isEnableRestVisionNotification() {
        return enableRestVisionNotification;
    }

    public boolean isEnableWaterIntakeNotification() {
        return enableWaterIntakeNotification;
    }

    public boolean isEnableWorkStandingNotification() {
        return enableWorkStandingNotification;
    }

    public static class Builder {
        private boolean enabledStretchingNotification;
        private boolean enableRestVisionNotification;
        private boolean enableWaterIntakeNotification;
        private boolean enableWorkStandingNotification;

        public Builder enabledStretchingNotification(boolean enabledStretchingNotification) {
            this.enabledStretchingNotification = enabledStretchingNotification;
            return this;
        }

        public Builder enableRestVisionNotification(boolean enableRestVisionNotification) {
            this.enableRestVisionNotification = enableRestVisionNotification;
            return this;
        }

        public Builder enableWaterIntakeNotification(boolean enableWaterIntakeNotification) {
            this.enableWaterIntakeNotification = enableWaterIntakeNotification;
            return this;
        }

        public Builder enableWorkStandingNotification(boolean enableWorkStandingNotification) {
            this.enableWorkStandingNotification = enableWorkStandingNotification;
            return this;
        }

        public ActivitySettings build() {
            return new ActivitySettings(this);
        }
    }
}
