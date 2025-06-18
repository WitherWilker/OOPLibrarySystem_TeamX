package CampusLibrarySystem.service;

import CampusLibrarySystem.model.Book;
import CampusLibrarySystem.repository.BookRepository;

import java.util.List;

public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.getAll();
    }

    public Book getByIsbn(String isbn) {
        return repository.getAll().stream()
                .filter(b -> b.getIsbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> getAvailableBooks() {
        return repository.getAll().stream()
                .filter(book -> book.getStatus().equalsIgnoreCase("Available"))
                .toList();
    }

    public boolean borrowBook(String isbn) {
        Book book = repository.getByIsbn(isbn);
        if (book != null && book.getStatus().equalsIgnoreCase("Available")) {
            book.setStatus("Borrowed");
            repository.saveAll();
            return true;
        }
        return false;
    }

    public void markAsAvailable(String isbn) {
        Book book = repository.getByIsbn(isbn);
        if (book != null) {
            book.setStatus("Available");
            repository.saveAll();
        }
    }

    public void addBook(Book book) {
        repository.getAll().add(book);
        repository.saveAll();
    }

    public void removeBook(String isbn) {
        repository.getAll().removeIf(book -> book.getIsbn().equalsIgnoreCase(isbn));
        repository.saveAll();
    }
}
