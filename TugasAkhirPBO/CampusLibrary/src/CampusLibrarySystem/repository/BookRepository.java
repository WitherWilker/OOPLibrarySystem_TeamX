package CampusLibrarySystem.repository;

import CampusLibrarySystem.model.Book;
import CampusLibrarySystem.util.CSVUtil;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private final String FILE_PATH = "resources/data/books.csv";
    private final List<Book> books = new ArrayList<>();

    public BookRepository() {
        loadAll();
    }

    public List<Book> getAll() {
        return books;
    }

    public void saveAll() {
        List<String[]> data = new ArrayList<>();
        for (Book b : books) {
            data.add(new String[]{
                    b.getIsbn(), b.getTitle(), b.getAuthor(), b.getGenre(), b.getStatus()
            });
        }
        CSVUtil.write(FILE_PATH, data);
    }

    public void loadAll() {
        books.clear();
        List<String[]> rows = CSVUtil.read(FILE_PATH);
        for (String[] row : rows) {
            books.add(new Book(row[0], row[1], row[2], row[3], row[4]));
        }
    }

    public Book getByIsbn(String isbn) {
        return books.stream()
                .filter(b -> b.getIsbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElse(null);
    }
}