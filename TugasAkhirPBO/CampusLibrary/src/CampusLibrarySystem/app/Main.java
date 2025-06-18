package CampusLibrarySystem.app;

import CampusLibrarySystem.gui.LoginPanel;
import CampusLibrarySystem.view.SceneManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Atur stage utama di SceneManager
        SceneManager.setStage(primaryStage);

        // Tampilkan halaman login pertama kali
        Scene loginScene = LoginPanel.getScene();
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Campus Library System");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}