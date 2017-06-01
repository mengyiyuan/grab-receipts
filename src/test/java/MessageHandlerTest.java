import com.google.api.services.gmail.Gmail;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageHandlerTest {
    private String user = "me";
    private Gmail service = new GmailServiceProvider(new GmailServiceConfigurer().loadDefaultConfiguration()).getGmailService();

    @Test
    public void testGetListMessageResponseBySearchQuery() throws Exception {
        assertEquals(2,
                new MessageHandler(service, user)
                        .getListMessageResponseBySearchQuery("from:(no-reply@grab.com) business receipt after:2017-05-28 before:2017-05-30")
                        .getMessages().size());
    }
}
