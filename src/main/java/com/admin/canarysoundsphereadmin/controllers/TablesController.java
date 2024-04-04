package com.admin.canarysoundsphereadmin.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TablesController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}