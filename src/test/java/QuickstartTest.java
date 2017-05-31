import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class QuickstartTest {
    @Test
    public void testDateFormat() throws Exception {
        assertEquals(2, LocalDateTime.now().getDayOfWeek().getValue());
    }
}
