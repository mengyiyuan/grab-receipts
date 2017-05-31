import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHandler {
    public int getTodayDayOfTheWeek() {
        return LocalDateTime.now().getDayOfWeek().getValue();
    }

    public String formatSearchQuery() {
        LocalDateTime lastSunday = LocalDateTime.now().minusDays(getTodayDayOfTheWeek());
        LocalDateTime thisSaturday = lastSunday.plusDays(6);
        return "from:(no-reply@grab.com) business receipt after:" + formatDate(lastSunday) + " before:" + formatDate(thisSaturday) + "";
    }

    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
