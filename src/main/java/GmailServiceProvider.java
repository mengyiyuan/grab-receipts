import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.gmail.Gmail;

import java.io.IOException;

class GmailServiceProvider {
    private GmailServiceConfigurer configurer;

    GmailServiceProvider(GmailServiceConfigurer configurer) {
        this.configurer = configurer;
    }

    Gmail getGmailService() {
        Credential credential = null;
        try {
            credential = configurer.authorize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gmail.Builder(configurer.getHttpTransport(), configurer.getJsonFactory(), credential)
                .setApplicationName(configurer.getApplicationName())
                .build();
    }

}
