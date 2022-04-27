package dtapcs.springframework.Formee.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
        @Value(value = "classpath:formee_firebase_sdk_key.json")
        private Resource serviceAccountResource;

        @Bean
        public FirebaseApp createFireBaseApp() throws IOException {
            InputStream serviceAccount = serviceAccountResource.getInputStream();

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            //Add loggers
            System.out.println("Firebase config initialized");

            return FirebaseApp.initializeApp(options);
        }
}
