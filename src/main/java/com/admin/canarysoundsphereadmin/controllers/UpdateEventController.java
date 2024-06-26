package com.admin.canarysoundsphereadmin.controllers;

import com.admin.canarysoundsphereadmin.models.EventClass;
import com.admin.canarysoundsphereadmin.models.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.showAlert;
import static com.admin.canarysoundsphereadmin.controllers.TablesController.eventId;
import static com.admin.canarysoundsphereadmin.models.EventManager.updateEventFieldById;

/**
 * Controlador para actualizar información de eventos.
 */
public class UpdateEventController implements Initializable {
    @FXML
    public TextArea newValueTextArea;
    @FXML
    public TextArea showEventUpdated;
    @FXML
    private Label title;
    @FXML
    private ComboBox fieldsComboBox;

    /**
     * Inicializa el controlador.
     *
     * @param url            La ubicación para resolver rutas relativas de la raíz del objeto url;
     *                       puede ser nulo si no se conoce la ubicación
     * @param resourceBundle El ResourceBundle que se puede usar para localizar objetos de cadena, puede ser nulo
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> fields = FXCollections.observableArrayList(
                "Logo", "Image", "Name", "Date", "Time", "Capacity"
                , "Description", "Direction", "Marker", "Ticket_store"
        );
        fieldsComboBox.setItems(fields);
    }

    /**
     * Maneja el clic en el botón de salida.
     */
    public void exitButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }

    public void sendButtonClicked() {
        String fieldName = (String) fieldsComboBox.getValue();
        String newValue = newValueTextArea.getText();
        System.out.println(fieldName);
        if (fieldName != null && !newValue.isEmpty()) {
            boolean updated = updateEventFieldById(eventId, fieldName.toLowerCase(), newValue, LoginController.token);
            if (updated) {
                showAlert("Éxito", "El evento se ha actualizado correctamente.", Alert.AlertType.INFORMATION);
                searchUpdateEvent();
            } else {
                showAlert("Error", "Error al actualizar el campo del evento.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Por favor, selecciona un campo y proporciona un nuevo valor.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Busca y muestra el evento actualizado.
     */
    public void searchUpdateEvent(){
        String eventId = TablesController.eventId;
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

            showEventUpdated.setText(eventText.toString());
        } else {
            showEventUpdated.setText("Evento no encontrado.");
        }
    }
}
