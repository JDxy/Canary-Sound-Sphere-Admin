package com.admin.canarysoundsphereadmin.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;

public class InsertAuthorController {
    @FXML
    private Label title;
    public void exitButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }

    public void sendButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }
}
