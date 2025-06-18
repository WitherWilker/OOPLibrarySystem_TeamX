package CampusLibrarySystem.gui;

import CampusLibrarySystem.auth.LoginService;
import CampusLibrarySystem.auth.Session;
import CampusLibrarySystem.model.Admin;
import CampusLibrarySystem.model.Member;
import CampusLibrarySystem.repository.MemberRepository;
import CampusLibrarySystem.util.CustomAlert;
import CampusLibrarySystem.view.SceneManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginPanel {

    public static Scene getScene() {
        // === LEFT PANEL ===
        VBox leftPane = new VBox();
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(40));

        Stop[] stops = new Stop[]{
                new Stop(0, Color.web("#f15a29")),
                new Stop(1, Color.web("#d91f5a"))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        leftPane.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        ImageView logo = new ImageView(new Image("file:resources/umm_logo.png"));
        logo.setFitWidth(150);
        logo.setFitHeight(150);
        logo.setPreserveRatio(true);
        StackPane logoContainer = new StackPane(logo);
        logoContainer.setPrefSize(160, 160);

        Text libraryText = new Text("LIBRARY");
        libraryText.setFill(Color.WHITE);
        libraryText.setFont(Font.font("Roboto", FontWeight.EXTRA_BOLD, 48));

        Text ummText = new Text("UMM");
        ummText.setFill(Color.WHITE);
        ummText.setFont(Font.font("Roboto", FontWeight.EXTRA_BOLD, 48));

        VBox textBox = new VBox(0);
        textBox.setAlignment(Pos.CENTER_LEFT);
        textBox.getChildren().addAll(libraryText, ummText);

        HBox leftContent = new HBox(24);
        leftContent.setAlignment(Pos.CENTER_LEFT);
        leftContent.setMaxWidth(400);
        leftContent.getChildren().addAll(logoContainer, textBox);
        leftPane.getChildren().add(leftContent);

        // === RIGHT PANEL ===
        VBox rightPane = new VBox();
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(40));
        rightPane.setBackground(new Background(new BackgroundFill(Color.web("#fff0f0"), CornerRadii.EMPTY, Insets.EMPTY)));

        VBox card = new VBox(24);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setMaxWidth(400);
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(12), Insets.EMPTY)));
        card.setEffect(new DropShadow(10, Color.gray(0, 0.2)));

        Text signInTitle = new Text("SIGN IN");
        signInTitle.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 36));
        signInTitle.setFill(Color.web("#4a5568"));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setFont(Font.font(16));
        usernameField.setPrefHeight(44);
        usernameField.setStyle("-fx-background-radius: 9999px; -fx-border-radius: 9999px; -fx-border-color: #d1d5db;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setFont(Font.font(16));
        passwordField.setPrefHeight(44);
        passwordField.setStyle("-fx-background-radius: 9999px; -fx-border-radius: 9999px; -fx-border-color: #d1d5db;");

        HBox buttonsBox = new HBox(24);
        buttonsBox.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("LOGIN");
        loginBtn.setFont(Font.font(16));
        loginBtn.setTextFill(Color.WHITE);
        loginBtn.setPrefWidth(140);
        loginBtn.setPrefHeight(44);
        loginBtn.setStyle("-fx-background-color: #d91f5a; -fx-background-radius: 9999px;");
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle("-fx-background-color: #b71c4a; -fx-background-radius: 9999px;"));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle("-fx-background-color: #d91f5a; -fx-background-radius: 9999px;"));

        Button registerBtn = new Button("REGISTER");
        registerBtn.setFont(Font.font(16));
        registerBtn.setTextFill(Color.WHITE);
        registerBtn.setPrefWidth(140);
        registerBtn.setPrefHeight(44);
        registerBtn.setStyle("-fx-background-color: #d91f5a; -fx-background-radius: 9999px;");
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle("-fx-background-color: #b71c4a; -fx-background-radius: 9999px;"));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle("-fx-background-color: #d91f5a; -fx-background-radius: 9999px;"));

        buttonsBox.getChildren().addAll(loginBtn, registerBtn);
        card.getChildren().addAll(signInTitle, usernameField, passwordField, buttonsBox);
        rightPane.getChildren().add(card);

        // === AKSI TOMBOL ===
        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            LoginService loginService = new LoginService(new MemberRepository());

            Admin admin = loginService.loginAsAdmin(username, password);
            if (admin != null) {
                Session.setCurrentUser(admin);
                SceneManager.switchScene(BookManagementPanel.getScene());
                return;
            }

            Member member = loginService.loginAsMember(username, password);
            if (member != null) {
                Session.setCurrentUser(member);
                SceneManager.switchScene(LibraryPanel.getScene());
                return;
            }

            CustomAlert.showInfo("Login Gagal!", "Username atau password salah.");
        });

        registerBtn.setOnAction(e -> SceneManager.switchScene(RegisterPanel.getScene()));

        // === ROOT LAYOUT ===
        HBox root = new HBox();
        root.getChildren().addAll(leftPane, rightPane);
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);
        leftPane.prefWidthProperty().bind(root.widthProperty().multiply(0.5));
        rightPane.prefWidthProperty().bind(root.widthProperty().multiply(0.5));

        return new Scene(root, 900, 600);
    }
}