package CampusLibrarySystem.model;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String status; // "Available", "Borrowed"

    public Book(String isbn, String title, String author, String genre, String status) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}