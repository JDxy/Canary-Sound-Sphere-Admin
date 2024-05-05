package com.admin.canarysoundsphereadmin.controllers;

import com.admin.canarysoundsphereadmin.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONException;
import java.io.IOException;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.models.LoginManager.signin;

public class LoginController {
    @FXML
    public Label title;
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField passwordTextField;
    public static String token;

    public void sendButton() throws IOException, JSONException {
        User user = new User(nameTextField.getText(), passwordTextField.getText());

        if (signin(user)!=null){
            token=signin(user);
            System.out.println(token);
            cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
        }
    }
}
