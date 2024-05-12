package com.admin.canarysoundsphereadmin.controllers;

import com.admin.canarysoundsphereadmin.models.Author;
import com.admin.canarysoundsphereadmin.models.AuthorManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.showAlert;
import static com.admin.canarysoundsphereadmin.controllers.TablesController.authorId;
import static com.admin.canarysoundsphereadmin.models.AuthorManager.updateAuthorFieldById;

public class UpdateAuthorController {
    public TextArea newValueTextArea;
    public TextArea showAuthorUpdated;
    @FXML
    private Label title;
    @FXML
    private ComboBox fieldsComboBox;
    public void initialize(){
        ObservableList<String> fields = FXCollections.observableArrayList(
                "Name", "Image", "Foundation_year", "Music_type", "Description", "Music_list"
        );
        fieldsComboBox.setItems(fields);    }
    public void exitButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }

    public void sendButtonClicked(){
        String fieldName = (String) fieldsComboBox.getValue();
        String newValue = newValueTextArea.getText();
        System.out.println(fieldName);
        if (fieldName != null && !newValue.isEmpty()) {
            boolean updated = updateAuthorFieldById(authorId, fieldName.toLowerCase(), newValue, LoginController.token);
            if (updated) {
                showAlert("Éxito","El autor se ha actualizado correctamente.", Alert.AlertType.ERROR);
                searchUpdateAuthor();
            } else {
                showAlert("Error","Error al actualizar el campo del evento.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error","Por favor, selecciona un campo y proporciona un nuevo valor.", Alert.AlertType.ERROR);
        }
    }

    public void searchUpdateAuthor() {
        String authorId = TablesController.authorId;
        Author foundAuthor = AuthorManager.getAuthorById(authorId);

        if (foundAuthor != null) {
            StringBuilder authorText = new StringBuilder();
            authorText.append("ID: ").append(foundAuthor.get_id()).append("\n");
            authorText.append("Logo: ").append(foundAuthor.getName()).append("\n");
            authorText.append("Image: ").append(foundAuthor.getImage()).append("\n");
            authorText.append("Name: ").append(foundAuthor.getFoundation_year()).append("\n");
            authorText.append("Date: ").append(foundAuthor.getMusic_type()).append("\n");
            authorText.append("Time: ").append(foundAuthor.getDescription()).append("\n");
            authorText.append("Ticket Store: ").append(foundAuthor.getMusic_list()).append("\n\n");

            showAuthorUpdated.setText(authorText.toString());
        } else {
            showAuthorUpdated.setText("No se encontraron resultados.");
        }
    }
}
