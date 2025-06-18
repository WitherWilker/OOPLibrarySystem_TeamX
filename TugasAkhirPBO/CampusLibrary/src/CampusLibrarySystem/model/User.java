package CampusLibrarySystem.model;

public abstract class User {
    protected String id;
    protected String name;
    protected String username;
    protected String password;
    protected String email;

    public User(String id, String name, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }

    public void setPassword(String password) { this.password = password; }
}
