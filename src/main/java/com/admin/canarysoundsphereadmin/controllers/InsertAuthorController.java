package com.admin.canarysoundsphereadmin.controllers;

import com.admin.canarysoundsphereadmin.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.showAlert;
import static com.admin.canarysoundsphereadmin.models.AuthorManager.idAuthorPlusOne;
import static com.admin.canarysoundsphereadmin.models.AuthorManager.insertAuthor;

/**
 * Controlador para la funcionalidad de insertar autores en la aplicación de administración de Canary Sound Sphere.
 * Gestiona la interfaz de usuario y las interacciones para insertar un nuevo autor.
 */
public class InsertAuthorController  implements Initializable {
    @FXML
    public TextArea showAuthor;
    @FXML
    public TextField search_by_id;
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

    /**
     * Inicializa la clase del controlador. Este método se llama automáticamente después de que el archivo FXML ha sido cargado.
     *
     * @param url            La URL utilizada para resolver el objeto raíz.
     * @param resourceBundle El recurso que se utiliza para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextArea();
    }

    /**
     * Maneja el evento de clic en el botón de salir.
     * Cambia la escena a la vista de tablas.
     */
    public void exitButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/tables-view.fxml", "Tablas", title);
    }

    /**
     * Maneja el evento de clic en el botón de enviar.
     * Recoge la información del formulario y la envía para insertar un nuevo autor.
     *
     * @throws JSONException Si ocurre un error al crear el objeto JSON.
     * @throws IOException   Si ocurre un error de entrada/salida.
     */
    public void sendButtonClicked() throws JSONException, IOException {
        String name = nameTextField.getText();
        String image = imageTextField.getText();
        String foundationYearStr = foundationYearTextField.getText();
        String musicType = musicTypeTextField.getText();
        String description = descriptionTextField.getText();
        String songsList = songsListsTextFields.getText();
        int foundationYear;

        if (name.isEmpty()) {
            showAlert("Error", "El campo Nombre no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        if (image.isEmpty()) {
            showAlert("Error", "El campo Imagen no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        try {
            foundationYear = Integer.parseInt(foundationYearStr);
        } catch (NumberFormatException e) {
            showAlert("Error", "El campo Año de fundacion debe ser un número entero", Alert.AlertType.ERROR);
            return;
        }
        if (musicType.isEmpty()) {
            showAlert("Error", "El campo Tipo de Música no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        if (description.isEmpty()) {
            showAlert("Error", "El campo Descripción no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        if (songsList.isEmpty()) {
            showAlert("Error", "El campo Listas no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }

        Author newAuthor = new Author(idAuthorPlusOne(), name, image, foundationYear, musicType, description, songsList);
        insertAuthor(newAuthor, LoginController.token);

        clean();
    }

    /**
     * Maneja el evento de clic en el botón de buscar por ID.
     * Busca un autor por su ID y muestra la información en el área de texto.
     *
     * @param mouseEvent El evento de clic del ratón.
     */
    public void search_by_id_button(MouseEvent mouseEvent) {
        String authorId = search_by_id.getText();
        Author foundAuthor = AuthorManager.getAuthorById(authorId);

        if (foundAuthor != null) {
            StringBuilder authorText = new StringBuilder();
            authorText.append("ID: ").append(foundAuthor.get_id()).append("\n");
            authorText.append("Name: ").append(foundAuthor.getName()).append("\n");
            authorText.append("Image: ").append(foundAuthor.getImage()).append("\n");
            authorText.append("Foundation year: ").append(foundAuthor.getFoundation_year()).append("\n");
            authorText.append("Music type: ").append(foundAuthor.getMusic_type()).append("\n");
            authorText.append("Description: ").append(foundAuthor.getDescription()).append("\n");
            authorText.append("Music List: ").append(foundAuthor.getMusic_list()).append("\n\n");

            showAuthor.setText(authorText.toString());
        } else {
            showAuthor.setText("No se encontraron resultados.");
        }
    }

    /**
     * Maneja el evento de clic en el botón de limpiar.
     * Limpia los campos del formulario.
     *
     * @param mouseEvent El evento de clic del ratón.
     */
    public void clean_button(MouseEvent mouseEvent) {
        clean();
    }

    /**
     * Limpia los campos del formulario y actualiza el área de texto con la lista de autores.
     */
    public void clean(){
        nameTextField.setText("");
        imageTextField.setText("");
        foundationYearTextField.setText("");
        musicTypeTextField.setText("");
        descriptionTextField.setText("");
        songsListsTextFields.setText("");
        search_by_id.setText("");
        setTextArea();
    }

    /**
     * Configura el área de texto con la lista de todos los autores.
     */
    public void setTextArea(){
        ObservableList<Author> authors = FXCollections.observableArrayList();
        try {
            authors.addAll(AuthorManager.getAllAuthors());
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder authorText = new StringBuilder();
        for (Author author : authors) {
            authorText.append("ID: ").append(author.get_id()).append("\n");
            authorText.append("Name: ").append(author.getName()).append("\n");
            authorText.append("Image: ").append(author.getImage()).append("\n");
            authorText.append("Foundation year: ").append(author.getFoundation_year()).append("\n");
            authorText.append("Music Type: ").append(author.getMusic_type()).append("\n");
            authorText.append("Description: ").append(author.getDescription()).append("\n");
            authorText.append("Music List: ").append(author.getMusic_list()).append("\n\n");
        }
        showAuthor.setText(authorText.toString());
    }
}
