package com.admin.canarysoundsphereadmin.controllers;

import com.admin.canarysoundsphereadmin.models.Author;
import com.admin.canarysoundsphereadmin.models.LoginManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONException;
import java.io.IOException;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.models.AuthorManager.idAuthorPlusOne;
import static com.admin.canarysoundsphereadmin.models.AuthorManager.insertAuthor;

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

    public void sendButtonClicked() throws JSONException, IOException {
        String name = nameTextField.getText();
        String image = imageTextField.getText();
        int foundationYear = Integer.parseInt(foundationYearTextField.getText());
        String musicType = musicTypeTextField.getText();
        String description = descriptionTextField.getText();
        String songsList = songsListsTextFields.getText();

        Author newAuthor = new Author(idAuthorPlusOne(), name, image, foundationYear, musicType, description, songsList);
        insertAuthor(newAuthor, LoginController.token);
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }
}
