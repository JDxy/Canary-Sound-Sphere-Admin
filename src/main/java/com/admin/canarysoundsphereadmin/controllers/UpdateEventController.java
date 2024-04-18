package com.admin.canarysoundsphereadmin.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.controllers.TablesController.eventId;
import static com.admin.canarysoundsphereadmin.models.DBManager.updateEventFieldById;

public class UpdateEventController {
    @FXML
    private Label title;

    @FXML
    private TextField newValueTextField;

    @FXML
    private ComboBox fieldsComboBox;

    public void initialize(){
        ObservableList<String> fields = FXCollections.observableArrayList(
                "Logo", "Image", "Name", "Date", "Time", "Capacity"
                , "Decription", "Direction", "Marker", "Ticket_store"
        );
        fieldsComboBox.setItems(fields);    }
    public void exitButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }

    public void sendButtonClicked() {
        String fieldName = (String) fieldsComboBox.getValue();
        String newValue = newValueTextField.getText();
        System.out.println(fieldName);
        if (fieldName != null && !newValue.isEmpty()) {
            boolean updated = updateEventFieldById(eventId, fieldName.toLowerCase(), newValue);
            if (updated) {
                cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
            } else {
                System.out.println("Error al actualizar el campo del evento.");
            }
        } else {
            System.out.println("Por favor, selecciona un campo y proporciona un nuevo valor.");
        }
    }
}
