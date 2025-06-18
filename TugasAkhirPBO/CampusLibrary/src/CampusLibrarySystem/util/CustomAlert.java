package CampusLibrarySystem.util;

import CampusLibrarySystem.model.Book;
import CampusLibrarySystem.model.Transaction;
import CampusLibrarySystem.service.BookService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Circle;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.text.TextAlignment;


public class CustomAlert {

    public static void showBorrowedPopup(List<Transaction> transactions, BookService bookService) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Buku Yang Telah Anda Pinjam");

        ImageView popupLogo = null;
        try {
            popupLogo = new ImageView(new Image(new FileInputStream("resources/umm_logo.png")));
            popupLogo.setFitWidth(100);
            popupLogo.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            popupLogo = null;
        }

        Label titleLabel = new Label("Buku Yang Telah Anda Pinjam");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#4b3ef5"));

        VBox listContainer = new VBox(20);
        listContainer.setPadding(new Insets(20));
        listContainer.setStyle("-fx-background-color: #eef3fd; -fx-background-radius: 16;");
        listContainer.setPrefWidth(350);

        for (Transaction tx : transactions) {
            Book book = bookService.getByIsbn(tx.getIsbn());
            if (book != null) {
                VBox itemBox = new VBox(5);
                itemBox.getChildren().addAll(
                        createInfoLabel("Name Book :", book.getTitle()),
                        createInfoLabel("Borrow Date :", DateUtil.format(tx.getBorrowDate())),
                        createInfoLabel("Return Date :", DateUtil.format(tx.getReturnDate())),
                        new Separator()
                );
                listContainer.getChildren().add(itemBox);
            }
        }

        VBox popupLayout = new VBox(20);
        popupLayout.setAlignment(Pos.TOP_CENTER);
        popupLayout.setPadding(new Insets(20));
        if (popupLogo != null) popupLayout.getChildren().add(popupLogo);
        popupLayout.getChildren().addAll(titleLabel, listContainer);

        Scene popupScene = new Scene(popupLayout, 420, 500);
        popup.setScene(popupScene);
        popup.showAndWait();
    }


    private static HBox createInfoLabel(String labelText, String valueText) {
        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        label.setTextFill(Color.web("#4b3ef5"));

        Label value = new Label(valueText);
        value.setFont(Font.font("Arial", 13));
        value.setTextFill(Color.web("#333"));

        return new HBox(10, label, value);
    }

    public static void showBorrowSuccessPopup(String transactionId, Book book) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Peminjaman Berhasil");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-border-color: #28a745; -fx-border-radius: 16;");

        SVGPath checkIcon = new SVGPath();
        checkIcon.setContent("M10 15.172l-3.95-3.95-1.414 1.414L10 18 20.364 7.636l-1.414-1.414z");
        checkIcon.setFill(Color.web("#28a745"));
        checkIcon.setScaleX(3.5);
        checkIcon.setScaleY(3.5);

        StackPane iconCircle = new StackPane(checkIcon);
        iconCircle.setPrefSize(100, 100);
        iconCircle.setStyle("-fx-background-color: #d4edda; -fx-background-radius: 50;");

        Label title = new Label("Peminjaman Buku\nBerhasil !");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 24));
        title.setTextFill(Color.web("#28a745"));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        addPopupRow(grid, 0, "Transaction ID:", transactionId);
        addPopupRow(grid, 1, "Book:", book.getTitle());
        addPopupRow(grid, 2, "Code:", book.getIsbn());
        addPopupRow(grid, 3, "Borrow Date:", DateUtil.format(LocalDate.now()));
        addPopupRow(grid, 4, "Return Date:", DateUtil.format(LocalDate.now().plusDays(7)));

        Label warning = new Label("Jika buku tidak dikembalikan sesuai jadwal,\nAnda akan dikenakan denda.");
        warning.setTextFill(Color.web("#dc3545"));
        warning.setFont(Font.font("Arial", FontPosture.ITALIC, 14));
        warning.setWrapText(true);
        warning.setAlignment(Pos.CENTER);

        Button closeBtn = new Button("Tutup");
        closeBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        closeBtn.setOnAction(e -> popup.close());

        layout.getChildren().addAll(iconCircle, title, grid, warning, closeBtn);

        Scene scene = new Scene(layout, 440, 520);
        popup.setScene(scene);
        popup.showAndWait();
    }


    public static void addPopupRow(GridPane grid, int row, String labelText, String valueText) {
        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(Color.web("#000"));

        Label value = new Label(valueText);
        value.setFont(Font.font("Arial", 14));
        value.setTextFill(Color.web("#333"));

        grid.addRow(row, label, value);
    }

    public static void showBookAdded(String title, String isbn) {
        String msg = "Buku \"" + title + "\" berhasil ditambahkan.\nISBN: " + isbn;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Buku Ditambahkan");
        setBoldHeader(alert,"Buku berhasil ditambahkan ke katalog");

        setResizableText(alert, msg);
        applyIcon(alert);
        alert.showAndWait();
    }

    public static void showBookRemoved(String title, String isbn) {
        String msg = "Buku \"" + title + "\" berhasil dihapus.\nISBN: " + isbn;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Buku Dihapus");
        setBoldHeader(alert,"Buku berhasil dihapus dari katalog");

        setResizableText(alert, msg);
        applyIcon(alert);
        alert.showAndWait();
    }

    public static void showMemberAdded(String name, String id) {
        String msg = "Member \"" + name + "\" berhasil ditambahkan.\nID: " + id;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Member Ditambahkan");
        setBoldHeader(alert,"Pendaftaran Member Berhasil");

        setResizableText(alert, msg);
        applyIcon(alert);
        alert.showAndWait();
    }

    public static void showMemberRemoved(String id) {
        String msg = "Member Telah Dihapus";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Member Dihapus");
        setBoldHeader(alert,"Penghapusan Member Berhasil");

        setResizableText(alert, msg);
        applyIcon(alert);
        alert.showAndWait();
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        setBoldHeader(alert,"Terjadi Kesalahan");

        setResizableText(alert, message);
        applyIcon(alert);
        alert.showAndWait();
    }

    public static void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        setBoldHeader(alert, "Login Gagal!");

        setResizableText(alert, message);
        applyIcon(alert);
        alert.showAndWait();
    }

    private static void setResizableText(Alert alert, String message) {
        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        textArea.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 16;");

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(textArea, 0, 0);

        alert.getDialogPane().setContent(content);
    }

    private static void setBoldHeader(Alert alert, String headerText) {
        Label boldHeader = new Label(headerText);
        boldHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-font-family: 'Arial';");
        alert.getDialogPane().setHeader(boldHeader);
    }

    private static void applyIcon(Alert alert) {
        try {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:resources/umm_logo.png"));
        } catch (Exception ignored) {
        }
    }

    public static void showPopup(String transactionId, String bookTitle, String bookCode,
                                 String borrowDate, String returnDate, String note) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Peminjaman Buku");

        // Root layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: white; -fx-border-color: #2ecc71; -fx-border-width: 2; -fx-border-radius: 12;");

        // Ikon dalam lingkaran hijau muda
        Circle circle = new Circle(50, Color.web("#d0f0d0")); // lingkaran hijau muda
        ImageView checkIcon = new ImageView(new Image("file:resources/check.png")); // Pastikan path gambar benar
        checkIcon.setFitHeight(125);
        checkIcon.setFitWidth(225);

        StackPane iconStack = new StackPane(circle, checkIcon);
        iconStack.setPadding(new Insets(10, 0, 0, 0));

        // Label sukses
        Label successLabel = new Label("Peminjaman Buku\nBerhasil !");
        successLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        successLabel.setTextFill(Color.web("#2e7d32"));
        successLabel.setTextAlignment(TextAlignment.CENTER);

        // Informasi transaksi
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(10);
        infoGrid.setVgap(8);
        infoGrid.setAlignment(Pos.CENTER);
        infoGrid.setPadding(new Insets(10));

        infoGrid.add(createBoldLabel("Transaction ID:"), 0, 0);
        infoGrid.add(new Label(transactionId), 1, 0);

        infoGrid.add(createBoldLabel("Book:"), 0, 1);
        infoGrid.add(new Label(bookTitle), 1, 1);

        infoGrid.add(createBoldLabel("Code:"), 0, 2);
        infoGrid.add(new Label(bookCode), 1, 2);

        infoGrid.add(createBoldLabel("Borrow Date:"), 0, 3);
        infoGrid.add(new Label(borrowDate), 1, 3);

        infoGrid.add(createBoldLabel("Return Date:"), 0, 4);
        infoGrid.add(new Label(returnDate), 1, 4);

        // Note label (dinamis)
        Label noteLabel = new Label(note);
        noteLabel.setFont(Font.font("Arial", FontPosture.ITALIC, 13));
        noteLabel.setTextAlignment(TextAlignment.CENTER);
        noteLabel.setWrapText(true);
        noteLabel.setMaxWidth(300);
        noteLabel.setAlignment(Pos.CENTER);

        if (note.toLowerCase().contains("terlambat") || note.toLowerCase().contains("denda")) {
            noteLabel.setTextFill(Color.RED);
        } else {
            noteLabel.setTextFill(Color.web("#2e7d32"));
        }

        // Tombol Tutup
        Button closeButton = new Button("Tutup");
        closeButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        closeButton.setOnAction(e -> popupStage.close());

        // Tambahkan semua ke root
        root.getChildren().addAll(iconStack, successLabel, infoGrid, noteLabel, closeButton);

        Scene scene = new Scene(root, 400, 500);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    // Helper method
    private static Label createBoldLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        return label;
    }



    private static HBox createInfoRow(String label, String value, boolean linkStyle) {
        Label keyLabel = new Label(label);
        keyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 13));

        if (linkStyle) {
            valueLabel.setTextFill(Color.web("#1976d2"));
        }

        HBox row = new HBox(5, keyLabel, valueLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }
}