package CampusLibrarySystem.gui;

import CampusLibrarySystem.model.Member;
import CampusLibrarySystem.repository.MemberRepository;
import CampusLibrarySystem.service.MemberService;
import CampusLibrarySystem.util.CustomAlert;
import CampusLibrarySystem.view.SceneManager;
import javafx.beans.binding.Bindings;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class RegisterPanel {

    public static Scene getScene() {
        MemberService memberService = new MemberService(new MemberRepository());

        // === LEFT PANEL ===
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(40));
        leftPanel.setAlignment(Pos.CENTER_LEFT);

        BackgroundImage bg = new BackgroundImage(
                new Image("file:resources/background.png", 400, 600, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        leftPanel.setBackground(new Background(bg));

        ImageView logo = new ImageView(new Image("file:resources/background.png"));
        logo.setFitWidth(100);
        logo.setFitHeight(100);

        Label title = new Label("LIBRARY\nUMM");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Poppins", FontWeight.BOLD, 24));
        title.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 4, 0, 0, 2);");

        HBox logoAndTextBox = new HBox(10, logo, title);
        logoAndTextBox.setAlignment(Pos.CENTER_LEFT);
        leftPanel.getChildren().add(logoAndTextBox);
        StackPane leftStack = new StackPane(leftPanel);
        leftStack.setPrefWidth(400);

        // === RIGHT PANEL ===
        VBox rightPanel = new VBox();
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setPadding(new Insets(50));
        rightPanel.setBackground(new Background(new BackgroundFill(Color.web("#fce7f3"), CornerRadii.EMPTY, Insets.EMPTY)));

        VBox card = new VBox(20);
        card.setPadding(new Insets(40));
        card.setMaxWidth(400);
        card.setAlignment(Pos.CENTER);
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(20), Insets.EMPTY)));
        card.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 5);");

        Label registerLabel = new Label("REGISTER");
        registerLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 28));
        registerLabel.setTextFill(Color.web("#000000"));

        TextField nameField = styledInput("Nama");
        TextField majorField = styledInput("Major");
        TextField emailField = styledInput("Email");
        TextField usernameField = styledInput("Username");

        PasswordField passwordField = styledPassword("Password");
        TextField visiblePasswordField = styledInput("Password");
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);

        PasswordField confirmPasswordField = styledPassword("Confirm Password");
        TextField visibleConfirmPasswordField = styledInput("Confirm Password");
        visibleConfirmPasswordField.setVisible(false);
        visibleConfirmPasswordField.setManaged(false);

        Bindings.bindBidirectional(passwordField.textProperty(), visiblePasswordField.textProperty());
        Bindings.bindBidirectional(confirmPasswordField.textProperty(), visibleConfirmPasswordField.textProperty());

        CheckBox showPass = new CheckBox("Show Password");
        showPass.setOnAction(e -> {
            boolean show = showPass.isSelected();
            visiblePasswordField.setVisible(show);
            visiblePasswordField.setManaged(show);
            passwordField.setVisible(!show);
            passwordField.setManaged(!show);

            visibleConfirmPasswordField.setVisible(show);
            visibleConfirmPasswordField.setManaged(show);
            confirmPasswordField.setVisible(!show);
            confirmPasswordField.setManaged(!show);
        });

        Button registerBtn = new Button("REGISTER");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setStyle("-fx-background-color: linear-gradient(to right, #e91e63, #c2185b); -fx-text-fill: white; -fx-font-weight: 600; -fx-font-size: 14px; -fx-background-radius: 12;");
        registerBtn.setPadding(new Insets(12));
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle("-fx-background-color: linear-gradient(to right, #c2185b, #880e4f); -fx-text-fill: white; -fx-font-weight: 600; -fx-font-size: 14px; -fx-background-radius: 12;"));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle("-fx-background-color: linear-gradient(to right, #e91e63, #c2185b); -fx-text-fill: white; -fx-font-weight: 600; -fx-font-size: 14px; -fx-background-radius: 12;"));

        registerBtn.setOnAction(e -> {
            String name = nameField.getText();
            String major = majorField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = showPass.isSelected() ? visiblePasswordField.getText() : passwordField.getText();
            String confirm = showPass.isSelected() ? visibleConfirmPasswordField.getText() : confirmPasswordField.getText();

            if (name.isEmpty() || major.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                CustomAlert.showError("Please fill all fields.");
                return;
            }

            if (!password.equals(confirm)) {
                CustomAlert.showError("Password do not match.");
                return;
            }

            String id = memberService.generateNextId();
            Member member = new Member(id, name, major, username, password, email);
            memberService.addMember(member);

            CustomAlert.showMemberAdded(member.getName(), member.getId());
            SceneManager.switchScene(LoginPanel.getScene());
        });

        card.getChildren().addAll(
                registerLabel,
                nameField, majorField, emailField, usernameField,
                passwordField, visiblePasswordField,
                confirmPasswordField, visibleConfirmPasswordField,
                showPass,
                registerBtn
        );

        rightPanel.getChildren().add(card);

        HBox root = new HBox(leftStack, rightPanel);
        root.setPrefSize(900, 600);

        return new Scene(root);
    }

    private static TextField styledInput(String placeholder) {
        TextField input = new TextField();
        input.setPromptText(placeholder);
        styleTextInput(input);
        return input;
    }

    private static PasswordField styledPassword(String placeholder) {
        PasswordField input = new PasswordField();
        input.setPromptText(placeholder);
        styleTextInput(input);
        return input;
    }

    private static void styleTextInput(TextInputControl input) {
        input.setFont(Font.font("Poppins", 14));
        input.setPrefHeight(45);
        input.setPrefWidth(330);
        input.setStyle("-fx-border-color: #d1d5db; -fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 0 10 0 10;");
        input.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                input.setStyle("-fx-border-color: #e91e63; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 0 10 0 10;");
            } else {
                input.setStyle("-fx-border-color: #d1d5db; -fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 0 10 0 10;");
            }
        });
    }
}