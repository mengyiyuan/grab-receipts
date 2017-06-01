import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class DateHandlerTest {
    @Test
    public void testFormatSearchQuery() throws Exception {
        assertEquals("from:(no-reply@grab.com) business receipt after:2017-05-28 before:2017-06-03", new DateHandler().formatSearchQuery());
    }
}
