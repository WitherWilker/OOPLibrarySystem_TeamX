package CampusLibrarySystem.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parse(String text) {
        return LocalDate.parse(text, FORMATTER);
    }

    public static String format(LocalDate date) {
        return date.format(FORMATTER);
    }

    public static long daysBetween(LocalDate from, LocalDate to) {
        return java.time.temporal.ChronoUnit.DAYS.between(from, to);
    }
}