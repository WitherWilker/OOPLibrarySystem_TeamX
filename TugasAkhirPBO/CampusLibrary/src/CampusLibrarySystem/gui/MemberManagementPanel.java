package CampusLibrarySystem.gui;

import CampusLibrarySystem.model.Book;
import CampusLibrarySystem.model.Member;
import CampusLibrarySystem.repository.MemberRepository;
import CampusLibrarySystem.service.MemberService;
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
import java.util.List;

public class MemberManagementPanel {

    private static TableView<Member> table;
    private static MemberService memberService = new MemberService(new MemberRepository());

    private static TextField nameField, majorField, usernameField, passwordField, emailField;
    private static TextField removeIdField;

    public static Scene getScene() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f2f4f7;");

        // HEADER
        Label title = new Label("MEMBER PANEL");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        ImageView logoView;
        try {
            FileInputStream input = new FileInputStream("resources/umm_logo.png");
            Image logo = new Image(input);
            logoView = new ImageView(logo);
            logoView.setFitHeight(25);
            logoView.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            logoView = new ImageView(); // placeholder jika tidak ada
        }

        HBox header = new HBox(10, logoView, title);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: linear-gradient(to right, #f85032, #e7386f);");

        // TABLE
        table = new TableView<>();
        table.setPlaceholder(new Label("No members available"));

        TableColumn<Member, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(90);

        TableColumn<Member, String> nameCol = new TableColumn<>("Nama");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(160);

        TableColumn<Member, String> majorCol = new TableColumn<>("Major");
        majorCol.setCellValueFactory(new PropertyValueFactory<>("major"));
        majorCol.setPrefWidth(160);

        TableColumn<Member, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameCol.setPrefWidth(160);

        TableColumn<Member, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordCol.setPrefWidth(160);

        TableColumn<Member, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(215);

        table.getColumns().addAll(idCol, nameCol, majorCol, usernameCol, passwordCol, emailCol);
        refreshTable();

        // ADD FORM
        nameField = new TextField(); nameField.setPromptText("Nama");
        majorField = new TextField(); majorField.setPromptText("Major");
        usernameField = new TextField(); usernameField.setPromptText("Username");
        passwordField = new TextField(); passwordField.setPromptText("Password");
        emailField = new TextField(); emailField.setPromptText("Email");

        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> addMember());

        HBox addBox = new HBox(10, nameField, majorField, usernameField, passwordField, emailField, addBtn);
        addBox.setAlignment(Pos.CENTER_LEFT);
        addBox.setPadding(new Insets(15));
        addBox.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #e0e0e0;");

        // REMOVE FORM
        removeIdField = new TextField(); removeIdField.setPromptText("ID");

        Button removeBtn = new Button("Remove");
        removeBtn.setStyle("-fx-background-color: #c8ccd1;");
        removeBtn.setOnAction(e -> removeMember());

        HBox removeBox = new HBox(10, removeIdField, removeBtn);
        removeBox.setAlignment(Pos.CENTER_LEFT);
        removeBox.setPadding(new Insets(15));
        removeBox.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #e0e0e0;");

        Button backBtn = new Button("Back");
        backBtn.setTextFill(Color.WHITE);
        backBtn.setStyle("-fx-background-color: linear-gradient(to right, #f85032, #e7386f);");
        backBtn.setOnAction(e -> SceneManager.switchScene(BookManagementPanel.getScene()));

        HBox backBox = new HBox(15, backBtn);
        backBox.setAlignment(Pos.CENTER_RIGHT);

        layout.getChildren().addAll(header, table, addBox, removeBox, backBox);
        return new Scene(layout, 1000, 600);
    }

    private static void refreshTable() {
        List<Member> members = memberService.getAllMembers();
        table.getItems().setAll(members);
    }

    private static void addMember() {
        String name = nameField.getText();
        String major = majorField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || major.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) return;

        String id = memberService.generateNextId();
        Member member = new Member(id, name, major, username, password, email);
        memberService.addMember(member);
        CustomAlert.showMemberAdded(member.getName(), member.getId());

        refreshTable();
        nameField.clear(); majorField.clear(); usernameField.clear(); passwordField.clear(); emailField.clear();
    }

    private static void removeMember() {
        String id = removeIdField.getText();

        Member target = memberService.getAllMembers().stream()
                .filter(b -> b.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        if (target != null) {
            memberService.removeMember(target.getId());
            CustomAlert.showMemberRemoved(target.getId());
            refreshTable();
        }

        refreshTable();
        removeIdField.clear();
    }
}
