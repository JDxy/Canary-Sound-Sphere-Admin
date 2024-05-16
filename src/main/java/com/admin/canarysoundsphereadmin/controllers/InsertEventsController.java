package com.admin.canarysoundsphereadmin.controllers;

import com.admin.canarysoundsphereadmin.models.EventClass;
import com.admin.canarysoundsphereadmin.models.EventManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.showAlert;
import static com.admin.canarysoundsphereadmin.models.EventManager.*;

public class InsertEventsController implements Initializable {
    @FXML
    public TextArea showEvent;
    @FXML
    public TextField search_by_id;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextArea();
    }

    public void exitButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }

    public void sendButtonClicked() {
        String name = nameTextField.getText();
        String logo = logoTextField.getText();
        String image = imageTextField.getText();
        String date = dateTextField.getText();
        String hour = hourTextField.getText();
        String capacitySTR = capacityTextField.getText();
        String description = descriptionTextField.getText();
        String direction = directionTextField.getText();
        String marker = markerTextField.getText();
        String ticketStore = ticketsStoreTextFields.getText();
        Integer capacity;

        // Verificar campos vacíos
        if (name.isEmpty()) {
            showAlert("Error", "El campo Nombre no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        if (logo.isEmpty()) {
            showAlert("Error", "El campo Logo no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        if (image.isEmpty()) {
            showAlert("Error", "El campo Imagen no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        if (date.isEmpty()) {
            showAlert("Error", "El campo Fecha no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        if (hour.isEmpty()) {
            showAlert("Error", "El campo Hora no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        try {
            capacity = Integer.parseInt(capacitySTR);
        } catch (NumberFormatException e) {
            showAlert("Error", "El campo Capacidad debe ser un número entero", Alert.AlertType.ERROR);
            return;
        }
        if (description.isEmpty()) {
            showAlert("Error", "El campo Descripción no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        if (direction.isEmpty()) {
            showAlert("Error", "El campo Dirección no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        if (marker.isEmpty()) {
            showAlert("Error", "El campo Marcador no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        if (ticketStore.isEmpty()) {
            showAlert("Error", "El campo Tienda de boletos no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }

        EventClass newEvent = new EventClass(idEventPlusOne(), logo, image, name, date, hour, capacity, description, direction, marker, ticketStore);
        insertEvent(newEvent, LoginController.token);

        clean();
    }


    public void search_by_id_button(MouseEvent mouseEvent) {
        String eventId = search_by_id.getText();
        EventClass foundEvent = EventManager.getEventById(eventId);

        if (foundEvent != null) {
            StringBuilder eventText = new StringBuilder();
            eventText.append("ID: ").append(foundEvent.get_id()).append("\n");
            eventText.append("Logo: ").append(foundEvent.getLogo()).append("\n");
            eventText.append("Image: ").append(foundEvent.getImage()).append("\n");
            eventText.append("Name: ").append(foundEvent.getName()).append("\n");
            eventText.append("Date: ").append(foundEvent.getDate()).append("\n");
            eventText.append("Time: ").append(foundEvent.getTime()).append("\n");
            eventText.append("Capacity: ").append(foundEvent.getCapacity()).append("\n");
            eventText.append("Description: ").append(foundEvent.getDescription()).append("\n");
            eventText.append("Direction: ").append(foundEvent.getDirection()).append("\n");
            eventText.append("Marker: ").append(foundEvent.getMarker()).append("\n");
            eventText.append("Ticket Store: ").append(foundEvent.getTicket_store()).append("\n\n");

            showEvent.setText(eventText.toString());
        } else {
            showEvent.setText("Evento no encontrado.");
        }
    }

    public void clean_button(MouseEvent mouseEvent) {
       clean();
    }

    public void clean(){
        nameTextField.setText("");
        logoTextField.setText("");
        imageTextField.setText("");
        dateTextField.setText("");
        hourTextField.setText("");
        capacityTextField.setText("");
        descriptionTextField.setText("");
        directionTextField.setText("");
        markerTextField.setText("");
        ticketsStoreTextFields.setText("");
        search_by_id.setText("");
        setTextArea();
    }

    public void setTextArea(){
        ObservableList<EventClass> events = FXCollections.observableArrayList();
        try {
            events.addAll(EventManager.getAllEvents());
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder eventsText = new StringBuilder();
        for (EventClass event : events) {
            eventsText.append("ID: ").append(event.get_id()).append("\n");
            eventsText.append("Logo: ").append(event.getLogo()).append("\n");
            eventsText.append("Image: ").append(event.getImage()).append("\n");
            eventsText.append("Name: ").append(event.getName()).append("\n");
            eventsText.append("Date: ").append(event.getDate()).append("\n");
            eventsText.append("Time: ").append(event.getTime()).append("\n");
            eventsText.append("Capacity: ").append(event.getCapacity()).append("\n");
            eventsText.append("Description: ").append(event.getDescription()).append("\n");
            eventsText.append("Direction: ").append(event.getDirection()).append("\n");
            eventsText.append("Marker: ").append(event.getMarker()).append("\n");
            eventsText.append("Ticket Store: ").append(event.getTicket_store()).append("\n\n");
        }
        showEvent.setText(eventsText.toString());
    }
 }
