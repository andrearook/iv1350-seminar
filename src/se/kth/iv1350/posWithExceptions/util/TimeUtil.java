package se.kth.iv1350.posWithExceptions.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * This class is a utility class handling time and time formatting.
 */
public final class TimeUtil {

    private TimeUtil() {

    }

    /**
     * Get the time when this method is called as a formatted <code>String</code>.
     *
     * @return the time right when the method is called as a formatted <code>String</code>.
     */
    public static String getLocalizedTimeNow() {
        LocalDateTime now = LocalDateTime.now();
        return formatTime(now);
    }

    /**
     * Will format the <code>LocalDateTime</code> object and return it as a <code>String</code>.
     *
     * @param time The <code>LocalDateTime</code> object that shall be formatted.
     * @return the <code>LocalDateTime</code> object as a formatted <code>String</code>.
     */
    public static String formatTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return time.format(formatter);
    }
}
