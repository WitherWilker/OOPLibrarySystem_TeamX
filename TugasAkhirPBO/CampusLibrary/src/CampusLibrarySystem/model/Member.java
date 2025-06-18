package CampusLibrarySystem.model;

public class Member extends User {
    private String major;

    public Member(String id, String name, String major, String username, String password, String email) {
        super(id, name, username, password, email);
        this.major = major;
    }

    public String getMajor() {
        return major;
    }
}
