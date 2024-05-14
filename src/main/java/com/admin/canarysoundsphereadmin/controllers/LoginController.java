package com.admin.canarysoundsphereadmin.controllers;

import com.admin.canarysoundsphereadmin.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONException;
import java.io.IOException;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.showAlert;
import static com.admin.canarysoundsphereadmin.models.LoginManager.signin;

public class LoginController {
    @FXML
    public Label title;
    @FXML
    public TextField nameTextField;
    @FXML
    public PasswordField passwordTextField;
    //Variable para guardar el token
    public static String token;

    public void sendButton() throws IOException, JSONException {
        User user = new User(nameTextField.getText(), passwordTextField.getText());
        if (nameTextField.getText().isEmpty() | passwordTextField.getText().isEmpty()) {
            showAlert("ERROR!", "Falta rellenar el nombre o la contraseña", Alert.AlertType.ERROR);
        } else {
            if (signin(user) != null) {
                token = signin(user);
                System.out.println(token);
                cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
            } else {
                showAlert("ERROR!", "Datos incorrectos", Alert.AlertType.ERROR);
            }
        }
    }
}
