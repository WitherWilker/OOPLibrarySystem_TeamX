package CampusLibrarySystem.util;

import java.time.LocalDate;

public class FineCalculator {

    public static long calculateFine(long daysLate) {
        return daysLate > 0 ? daysLate * 2000 : 0;
    }

    public static long calculateFine(LocalDate dueDate, LocalDate returnDate) {
        long days = DateUtil.daysBetween(dueDate, returnDate);
        return calculateFine(days);
    }
}