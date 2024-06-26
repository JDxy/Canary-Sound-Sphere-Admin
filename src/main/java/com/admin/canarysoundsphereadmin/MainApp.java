package com.admin.canarysoundsphereadmin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // Cargar el ícono desde los recursos
        Image icon = new Image(getClass().getResourceAsStream("/assets/images/CanarySoundSphereLogo.jpg"));

        // Establecer el ícono para la ventana principal
        stage.getIcons().add(icon);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}