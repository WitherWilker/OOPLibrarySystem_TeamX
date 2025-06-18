package CampusLibrarySystem.gui;

import CampusLibrarySystem.model.Book;
import CampusLibrarySystem.repository.BookRepository;
import CampusLibrarySystem.service.BookService;
import CampusLibrarySystem.util.CustomAlert;
import CampusLibrarySystem.view.SceneManager;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class BookManagementPanel {

    private static TableView<Book> table;
    private static final BookService bookService = new BookService(new BookRepository());

    private static TextField isbnField, titleField, genreField, authorField;
    private static ComboBox<String> statusBox;
    private static TextField removeIsbnField, removeTitleField;

    public static Scene getScene() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #f2f4f7;");

        // HEADER
        Label headerText = new Label("LIBRARY PANEL");
        headerText.setTextFill(Color.WHITE);
        headerText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        ImageView logoView;
        try {
            FileInputStream input = new FileInputStream("resources/umm_logo.png");
            Image logo = new Image(input);
            logoView = new ImageView(logo);
            logoView.setFitHeight(40);
            logoView.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            logoView = new ImageView();
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label greeting = new Label("Halo, Mimin 👋");
        greeting.setTextFill(Color.WHITE);
        greeting.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        HBox header = new HBox(10, logoView, headerText, spacer, greeting);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: linear-gradient(to right, #f85032, #e7386f);");

        // TABLE
        table = new TableView<>();
        table.setPlaceholder(new Label("No books available"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> genreCol = new TableColumn<>("Genre");
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(isbnCol, titleCol, genreCol, authorCol, statusCol);
        refreshTable();

        // ADD FORM
        isbnField = new TextField(); isbnField.setPromptText("ISBN");
        titleField = new TextField(); titleField.setPromptText("Title");
        genreField = new TextField(); genreField.setPromptText("Genre");
        authorField = new TextField(); authorField.setPromptText("Author");

        statusBox = new ComboBox<>();
        statusBox.getItems().addAll("Available", "Borrowed");
        statusBox.setPromptText("Status");

        Button addBtn = new Button("Add");
        addBtn.setStyle("-fx-background-color: #d2d5dc;");
        addBtn.setOnAction(e -> addBook());

        HBox addForm = new HBox(10, isbnField, titleField, genreField, authorField, statusBox, addBtn);
        addForm.setAlignment(Pos.CENTER_LEFT);
        addForm.setPadding(new Insets(15));
        addForm.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #e0e0e0;");

        // REMOVE FORM
        removeIsbnField = new TextField(); removeIsbnField.setPromptText("ISBN");
        removeTitleField = new TextField(); removeTitleField.setPromptText("Name");

        Button removeBtn = new Button("Remove");
        removeBtn.setStyle("-fx-background-color: #c8ccd1;");
        removeBtn.setOnAction(e -> removeBook());

        Button toMemberBtn = new Button("Member Management");
        toMemberBtn.setTextFill(Color.WHITE);
        toMemberBtn.setStyle("-fx-background-color: linear-gradient(to right, #f85032, #e7386f);");
        toMemberBtn.setOnAction(e -> SceneManager.switchScene(MemberManagementPanel.getScene()));

        HBox removeForm = new HBox(10, removeIsbnField, removeTitleField, removeBtn, toMemberBtn);
        removeForm.setAlignment(Pos.CENTER_LEFT);
        removeForm.setPadding(new Insets(15));
        removeForm.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #e0e0e0;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setTextFill(Color.WHITE);
        logoutBtn.setStyle("-fx-background-color: linear-gradient(to right, #f85032, #e7386f);");
        logoutBtn.setOnAction(e -> SceneManager.switchScene(LoginPanel.getScene()));

        HBox logoutForm = new HBox(15, logoutBtn);
        logoutForm.setAlignment(Pos.CENTER_RIGHT);

        layout.getChildren().addAll(header, table, addForm, removeForm, logoutForm);
        return new Scene(layout, 1000, 600);
    }

    private static void refreshTable() {
        List<Book> books = bookService.getAllBooks();
        table.getItems().setAll(books);
    }

    private static void addBook() {
        String isbn = isbnField.getText();
        String title = titleField.getText();
        String genre = genreField.getText();
        String author = authorField.getText();
        String status = statusBox.getValue();

        if (isbn.isEmpty() || title.isEmpty() || genre.isEmpty() || author.isEmpty() || status == null) return;

        Book book = new Book(isbn, title, author, genre, status);
        bookService.addBook(book);
        CustomAlert.showBookAdded(book.getTitle(), book.getIsbn());

        refreshTable();
        isbnField.clear(); titleField.clear(); genreField.clear(); authorField.clear(); statusBox.getSelectionModel().clearSelection();
    }

    private static void removeBook() {
        String isbn = removeIsbnField.getText();
        String title = removeTitleField.getText();

        Book target = bookService.getAllBooks().stream()
                .filter(b -> b.getIsbn().equals(isbn) && b.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (target != null) {
            bookService.removeBook(target.getIsbn());
            CustomAlert.showBookRemoved(target.getTitle(), target.getIsbn());
            refreshTable();
        }

        removeIsbnField.clear(); removeTitleField.clear();
    }
}