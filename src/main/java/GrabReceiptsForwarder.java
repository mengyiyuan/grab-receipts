import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class GrabReceiptsForwarder {

    public static void main(String[] args) throws IOException, MessagingException, GeneralSecurityException {
        Gmail service = new GmailServiceProvider(new GmailServiceConfigurer().loadDefaultConfiguration()).getGmailService();
        String user = "me";

        MessageHandler messageHandler = new MessageHandler(service, user);
        ListMessagesResponse response = messageHandler.getListMessageResponseBySearchQuery(new DateHandler().formatSearchQuery());
        messageHandler.sendListMessages(response, "receipts@expensify.com");
    }

}