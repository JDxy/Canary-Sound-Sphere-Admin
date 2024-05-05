package com.admin.canarysoundsphereadmin.controllers;

import com.admin.canarysoundsphereadmin.models.EventClass;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.models.EventManager.idEventPlusOne;
import static com.admin.canarysoundsphereadmin.models.EventManager.insertEvent;

public class InsertEventsController {
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField logoTextField;

    @FXML
    private TextField imageTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private TextField hourTextField;

    @FXML
    private TextField capacityTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField directionTextField;

    @FXML
    private TextField markerTextField;

    @FXML
    private TextField ticketsStoreTextFields;

    @FXML
    private Label title;

    public void exitButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }

    public void sendButtonClicked(){
        String name = nameTextField.getText();
        String logo = logoTextField.getText();
        String image = imageTextField.getText();
        String date = dateTextField.getText();
        String hour = hourTextField.getText();
        int capacity = Integer.parseInt(capacityTextField.getText());
        String description = descriptionTextField.getText();
        String direction = directionTextField.getText();
        String marker = markerTextField.getText();
        String ticketStore = ticketsStoreTextFields.getText();

        // Crear una nueva instancia de Event
        EventClass newEvent = new EventClass(idEventPlusOne(), logo, image, name, date, hour, capacity, description, direction, marker, ticketStore);
        insertEvent(newEvent,LoginController.token);
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }
}
