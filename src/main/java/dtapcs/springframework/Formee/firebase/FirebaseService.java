package dtapcs.springframework.Formee.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseService {
    private static String  jsonPath = "./formee_firebase_sdk_key.json";
    public static void InitFirebase() throws IOException {
        //Initialize firebase
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(jsonPath)))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
