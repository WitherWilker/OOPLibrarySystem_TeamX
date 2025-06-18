package CampusLibrarySystem.gui;

    import CampusLibrarySystem.auth.Session;
    import CampusLibrarySystem.model.Book;
    import CampusLibrarySystem.model.Member;
    import CampusLibrarySystem.model.Transaction;
    import CampusLibrarySystem.repository.BookRepository;
    import CampusLibrarySystem.repository.TransactionRepository;
    import CampusLibrarySystem.service.BookService;
    import CampusLibrarySystem.service.TransactionService;
    import CampusLibrarySystem.util.DateUtil;
    import CampusLibrarySystem.util.FineCalculator;
    import CampusLibrarySystem.view.SceneManager;
    import CampusLibrarySystem.util.CustomAlert;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

public class LibraryPanel {

    private static TableView<Book> table;
    private static TextField borrowIsbnField;
    private static TextField returnIsbnField;
    private static TextField searchField;

    private static final BookService bookService = new BookService(new BookRepository());
    private static final TransactionService transactionService = new TransactionService(new TransactionRepository());
    private static final Label borrowInfoLabel = new Label();

    public static Scene getScene() {
        VBox layout = new VBox(10); // spacing antar elemen
        layout.setPadding(new Insets(0, 20, 20, 20)); // atas = 0 agar tidak ada spasi kosong di atas tabel
        layout.setStyle("-fx-background-color: #f2f4f7;");

        // HEADER
        Label title = new Label("LIBRARY UMM");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);

        Member current = (Member) Session.getCurrentUser();
        Label userLabel = new Label("ID: " + current.getId() + " | " + current.getUsername());
        userLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        userLabel.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ImageView logoView;
        try {
            FileInputStream input = new FileInputStream("resources/umm_logo.png");
            Image logo = new Image(input);
            logoView = new ImageView(logo);
            logoView.setFitHeight(30);
            logoView.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            logoView = new ImageView();
        }

        HBox header = new HBox(10, logoView, title, spacer, userLabel);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: linear-gradient(to right, #f85032, #e7386f); -fx-background-radius: 8px;");

        // SEARCH FIELD
        searchField = new TextField();
        searchField.setPromptText("Search books...");
        searchField.setPrefWidth(300);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            List<Book> filtered = bookService.getAllBooks().stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(newVal.toLowerCase()) ||
                            book.getIsbn().toLowerCase().contains(newVal.toLowerCase()) ||
                            book.getAuthor().toLowerCase().contains(newVal.toLowerCase()) ||
                            book.getGenre().toLowerCase().contains(newVal.toLowerCase()))
                    .toList();
            table.getItems().setAll(filtered);
        });

        // BORROW INFO LABEL
        borrowInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        borrowInfoLabel.setTextFill(Color.DIMGRAY);

        // GABUNGKAN SEARCH + INFO DALAM 1 BARIS
        HBox topBar = new HBox(20, searchField, borrowInfoLabel);
        topBar.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(searchField, Priority.ALWAYS);
        topBar.setPadding(new Insets(0));

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

        VBox tableContainer = new VBox(table);
        tableContainer.setPadding(new Insets(0)); // tanpa jarak atas
        tableContainer.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 6px;");

        // BORROW
        borrowIsbnField = new TextField();
        borrowIsbnField.setPromptText("ISBN");
        Button borrowBtn = new Button("Borrow");
        borrowBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        borrowBtn.setOnAction(e -> borrowBook());

        HBox borrowBox = new HBox(10, borrowIsbnField, borrowBtn);
        borrowBox.setAlignment(Pos.CENTER_LEFT);
        borrowBox.setPadding(new Insets(15));
        borrowBox.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #e0e0e0;");

        // RETURN
        returnIsbnField = new TextField();
        returnIsbnField.setPromptText("ISBN");
        Button returnBtn = new Button("Return");
        returnBtn.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        returnBtn.setOnAction(e -> returnBook());

        HBox returnBox = new HBox(10, returnIsbnField, returnBtn);
        returnBox.setAlignment(Pos.CENTER_LEFT);
        returnBox.setPadding(new Insets(15));
        returnBox.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #e0e0e0;");

        // CHECK + LOGOUT
        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: linear-gradient(to right, #f85032, #e7386f); -fx-text-fill: white;");
        logoutBtn.setOnAction(e -> SceneManager.switchScene(LoginPanel.getScene()));

        Button checkBtn = new Button("Check");
        checkBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        checkBtn.setOnAction(e -> {
            Member user = (Member) Session.getCurrentUser();
            List<Transaction> txList = transactionService.findAllBorrowedByMember(user.getId());
            CustomAlert.showBorrowedPopup(txList, bookService);
        });

        HBox logoutBox = new HBox(10, checkBtn, logoutBtn);
        logoutBox.setAlignment(Pos.CENTER_RIGHT);

        // TAMBAHKAN KE LAYOUT UTAMA
        layout.getChildren().addAll(header, topBar, tableContainer, borrowBox, returnBox, logoutBox);
        return new Scene(layout, 1000, 600);
    }


    private static void refreshTable() {
        table.getItems().setAll(bookService.getAllBooks());
    }


    private static void borrowBook() {
        String isbn = borrowIsbnField.getText();
        if (isbn.isEmpty()) return;

        Member current = (Member) Session.getCurrentUser();
        if (bookService.borrowBook(isbn)) {
            transactionService.createTransaction(current.getId(), isbn);
            Book borrowed = bookService.getByIsbn(isbn);
            CustomAlert.showBorrowSuccessPopup(transactionService.generateNextId(), borrowed);

            refreshTable();
        } else {
            CustomAlert.showError("Buku tidak tersedia.");
        }
        borrowIsbnField.clear();
    }

    private static void returnBook() {
        String isbn = returnIsbnField.getText();
        if (isbn.isEmpty()) return;

        Member current = (Member) Session.getCurrentUser();
        Transaction tx = transactionService.findBorrowedTransaction(current.getId(), isbn);
        if (tx == null) {
            CustomAlert.showError("Tidak ditemukan transaksi peminjaman.");
            return;
        }

        LocalDate now = LocalDate.now();
        long daysLate = DateUtil.daysBetween(tx.getReturnDate(), now);
        long fine = FineCalculator.calculateFine(daysLate);

        transactionService.returnBook(tx.getId(), now, fine);
        bookService.markAsAvailable(isbn);

        String note = (fine > 0) ? "Terlambat " + daysLate + " hari. Denda: Rp" + fine : "Terima kasih telah mengembalikan tepat waktu!";

        Book returnedBook = bookService.getByIsbn(isbn);
        if (returnedBook != null) {
            // Panggil popup pengembalian khusus
            CustomAlert.showPopup(
                    tx.getId(),
                    returnedBook.getTitle(),
                    returnedBook.getIsbn(),
                    DateUtil.format(tx.getBorrowDate()),
                    DateUtil.format(tx.getReturnDate()),
                    note
            );
        }

        refreshTable();
        returnIsbnField.clear();
    }
}