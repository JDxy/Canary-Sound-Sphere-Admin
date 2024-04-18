package com.admin.canarysoundsphereadmin.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;

public class InsertAuthorController {
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField imageTextField;

    @FXML
    private TextField foundationYearTextField;

    @FXML
    private TextField musicTypeTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField songsListsTextFields;

    @FXML
    private Label title;

    public void exitButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }

    public void sendButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }
}
