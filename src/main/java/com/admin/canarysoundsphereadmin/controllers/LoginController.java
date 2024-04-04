package com.admin.canarysoundsphereadmin.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONException;

import java.io.IOException;

import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.models.DBManager.findAdmin;

public class LoginController {
    @FXML
    public Label title;
    @FXML
    public TextField nameTextField;

    @FXML
    public TextField passwordTextField;

    public void sendButton() throws IOException, JSONException {
       if (findAdmin(nameTextField.getText(), passwordTextField.getText())){
           cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
        };
    }
}
