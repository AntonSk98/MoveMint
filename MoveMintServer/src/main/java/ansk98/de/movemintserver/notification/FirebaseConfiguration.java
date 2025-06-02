package ansk98.de.movemintserver.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Configuration to set up Firebase services for use within the application.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
@Configuration
@ConditionalOnProperty(value = "firebase.enabled", matchIfMissing = true)
public class FirebaseConfiguration {

    @Value("${firebase.credentials}")
    private String credentials;

    @Bean
    FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }

    @Bean
    FirebaseApp firebaseApp(GoogleCredentials credentials) {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    GoogleCredentials googleCredentials() throws IOException {
        Objects.requireNonNull(credentials);

        try (InputStream is = new ByteArrayInputStream(credentials.getBytes())) {
            return GoogleCredentials.fromStream(is);
        }
    }
}
