package CampusLibrarySystem.util;

public class Validator {

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^\\S+@\\S+\\.\\S+$");
    }

    public static boolean isPasswordMatch(String p1, String p2) {
        return p1 != null && p1.equals(p2);
    }
}