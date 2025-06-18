package CampusLibrarySystem.auth;

import CampusLibrarySystem.model.Admin;
import CampusLibrarySystem.model.Member;
import CampusLibrarySystem.model.User;

public class AuthManager {

    public static boolean isAdmin() {
        User u = Session.getCurrentUser();
        return u != null && u instanceof Admin;
    }

    public static boolean isMember() {
        User u = Session.getCurrentUser();
        return u != null && u instanceof Member;
    }

    public static String getCurrentUserId() {
        User u = Session.getCurrentUser();
        return u != null ? u.getId() : null;
    }

    public static String getCurrentUserName() {
        User u = Session.getCurrentUser();
        return u != null ? u.getName() : null;
    }
}