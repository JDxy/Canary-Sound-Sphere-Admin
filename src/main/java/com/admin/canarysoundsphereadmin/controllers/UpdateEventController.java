package com.admin.canarysoundsphereadmin.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;

public class UpdateEventController {
    @FXML
    private Label title;
    @FXML
    private ComboBox fieldsComboBox;
    public void initialize(){
        ObservableList<String> fields = FXCollections.observableArrayList(
                "Logo", "Imagen", "Nombre", "Fecha", "Hora", "Capacidad"
                , "Decripcion", "Direccion", "Marcador", "Tienda de tickets"
        );
        fieldsComboBox.setItems(fields);    }
    public void exitButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }

    public void sendButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }
}
