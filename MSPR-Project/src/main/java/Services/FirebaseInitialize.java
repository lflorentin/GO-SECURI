package Services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class FirebaseInitialize {

    @PostConstruct
    public void Initialize() throws IOException {


        try {
            FileInputStream serviceAccount = new FileInputStream("./accountServiceKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://mspr-b3.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        }
        catch(Exception e){ e.printStackTrace(); }
    }
}
