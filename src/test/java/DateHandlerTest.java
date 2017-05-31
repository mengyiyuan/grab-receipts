import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class DateHandlerTest {
    @Test
    public void testFindThisMonday() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        assertEquals(LocalDate.of(2017, 5, 29).getDayOfYear(), now.minusDays(now.getDayOfWeek().getValue() - 1).getDayOfYear());
    }

    @Test
    public void testDateTimeFormat() throws Exception {
        assertEquals("2017-05-31", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @Test
    public void testFormatSearchQuery() throws Exception {
        assertEquals("from:(no-reply@grab.com) business receipt after:2017-05-28 before:2017-06-03", new DateHandler().formatSearchQuery());
    }
}
