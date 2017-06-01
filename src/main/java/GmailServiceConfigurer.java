import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

class GmailServiceConfigurer {
    private String applicationName;
    private HttpTransport httpTransport;
    private JsonFactory jsonFactory;
    private List<String> scopes;
    private FileDataStoreFactory dataStoreFactory;

    GmailServiceConfigurer() {
    }

    GmailServiceConfigurer(String applicationName, HttpTransport httpTransport, JsonFactory jsonFactory, List<String> scopes, FileDataStoreFactory dataStoreFactory) {
        this.applicationName = applicationName;
        this.httpTransport = httpTransport;
        this.jsonFactory = jsonFactory;
        this.scopes = scopes;
        this.dataStoreFactory = dataStoreFactory;
    }

    GmailServiceConfigurer loadDefaultConfiguration() {
        final java.io.File DATA_STORE_DIR = new java.io.File(
                System.getProperty("user.home"), ".credentials/gmail-java-quickstart");
        this.applicationName = "Gmail API Java GrabReceiptsForwarder";
        try {
            this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            this.dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        this.jsonFactory = JacksonFactory.getDefaultInstance();
        this.scopes = Collections.singletonList(GmailScopes.GMAIL_MODIFY);

        return this;
    }

    String getApplicationName() {
        return applicationName;
    }

    HttpTransport getHttpTransport() {
        return httpTransport;
    }

    JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    Credential authorize() throws IOException {
        GoogleClientSecrets clientSecrets = getClientSecrets();

        GoogleAuthorizationCodeFlow flow = buildAuthorizationCodeFlow(clientSecrets);

        return new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
    }

    private GoogleAuthorizationCodeFlow buildAuthorizationCodeFlow(GoogleClientSecrets clientSecrets) throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(dataStoreFactory)
                .setAccessType("offline")
                .build();
    }

    private GoogleClientSecrets getClientSecrets() throws IOException {
        InputStream in =
                GrabReceiptsForwarder.class.getResourceAsStream("/client_secret.json");
        return GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
    }
}
