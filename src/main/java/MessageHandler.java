import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

class MessageHandler {
    private final Gmail service;
    private final String user;

    MessageHandler(Gmail service, String user) {
        this.service = service;
        this.user = user;
    }

    ListMessagesResponse getListMessageResponseBySearchQuery(String query) throws IOException {
        return service.users()
                .messages().list(user)
                .setQ(query)
                .execute();
    }

    void sendListMessages(ListMessagesResponse messagesResponse, String to) throws IOException, MessagingException {
        List<Message> messages = messagesResponse.getMessages();
        for (Message message: messages) {
            message = service.users().messages().get(user, message.getId()).execute();
            MimeMessage mimeMessage = getMimeMessage(message.getId());
            mimeMessage.setHeader("To", to);
            sendMessage(mimeMessage);
        }
    }


    private MimeMessage getMimeMessage(String messageId)
            throws IOException, MessagingException {
        Message message = service.users().messages().get(user, messageId).setFormat("raw").execute();

        byte[] emailBytes = Base64.decodeBase64(message.getRaw());

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        return new MimeMessage(session, new ByteArrayInputStream(emailBytes));
    }

    private void sendMessage(MimeMessage email)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(email);
        message = service.users().messages().send(user, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
    }

    private static Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        email.writeTo(baos);
        String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}
