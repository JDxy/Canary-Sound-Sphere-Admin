package com.admin.canarysoundsphereadmin.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.controllers.TablesController.authorId;
import static com.admin.canarysoundsphereadmin.models.AuthorManager.updateAuthorFieldById;

public class UpdateAuthorController {
    public TextArea newValueTextArea;
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
                cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
            } else {
                System.out.println("Error al actualizar el campo del evento.");
            }
        } else {
            System.out.println("Por favor, selecciona un campo y proporciona un nuevo valor.");
        }
    }
}
