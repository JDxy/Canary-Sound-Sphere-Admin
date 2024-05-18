package com.admin.canarysoundsphereadmin.controllers;

import com.admin.canarysoundsphereadmin.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.showAlert;
import static com.admin.canarysoundsphereadmin.models.AuthorManager.deleteAuthorById;
import static com.admin.canarysoundsphereadmin.models.EventManager.*;

public class TablesController implements Initializable {
    // Events table
    @FXML
    private Label eventsTitle;
    @FXML
    private TableView<EventClass> eventsTable;
    @FXML
    private TableColumn<EventClass, String> idEventsColumn;
    @FXML
    private TableColumn<EventClass, String> logoEventsColumn;
    @FXML
    private TableColumn<EventClass, String> imageEventsColumn;
    @FXML
    private TableColumn<EventClass, String> nameEventsColumn;
    @FXML
    private TableColumn<EventClass, String> dateEventsColumn;
    @FXML
    private TableColumn<EventClass, String> timeEventsColumn;
    @FXML
    private TableColumn<EventClass, Integer> capacityEventsColumn;
    @FXML
    private TableColumn<EventClass, String> descriptionEventsColumn;
    @FXML
    private TableColumn<EventClass, String> directionEventsColumn;
    @FXML
    private TableColumn<EventClass, String> markerEventsColumn;
    @FXML
    private TableColumn<EventClass, String> ticket_storeEventsColumn;

    // Authors table
    @FXML
    private TableView<Author> authorsTable;
    @FXML
    private TableColumn<Author, String> idAuthorsColumn;
    @FXML
    private TableColumn<Author, String> nameAuthorsColumn;
    @FXML
    private TableColumn<Author, String> imageAuthorsColumn;
    @FXML
    private TableColumn<Author, Integer> foundation_yearAuthorsColumn;
    @FXML
    private TableColumn<Author, String> music_typeAuthorsColumn;
    @FXML
    private TableColumn<Author, String> descriptionAuthorsColumn;
    @FXML
    private TableColumn<Author, String> music_listAuthorsColumn;
    //Variable para guardar el eventid
    public static String eventId;
    //Variable para guardar el authorid
    public static String authorId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add content event table
        idEventsColumn.setCellValueFactory(new PropertyValueFactory<>("_id"));
        logoEventsColumn.setCellValueFactory(new PropertyValueFactory<>("logo"));
        imageEventsColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameEventsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateEventsColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeEventsColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        capacityEventsColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        descriptionEventsColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        directionEventsColumn.setCellValueFactory(new PropertyValueFactory<>("direction"));
        markerEventsColumn.setCellValueFactory(new PropertyValueFactory<>("marker"));
        ticket_storeEventsColumn.setCellValueFactory(new PropertyValueFactory<>("ticket_store"));

        ObservableList<EventClass> events = FXCollections.observableArrayList();
        try {
            events.addAll(EventManager.getAllEvents());
        } catch (Exception e) {
            e.printStackTrace();
        }

        eventsTable.setItems(events);

        // Add content authors table
        idAuthorsColumn.setCellValueFactory(new PropertyValueFactory<>("_id"));
        nameAuthorsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        imageAuthorsColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        foundation_yearAuthorsColumn.setCellValueFactory(new PropertyValueFactory<>("foundation_year"));
        music_typeAuthorsColumn.setCellValueFactory(new PropertyValueFactory<>("music_type"));
        descriptionAuthorsColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        music_listAuthorsColumn.setCellValueFactory(new PropertyValueFactory<>("music_list"));

        ObservableList<Author> authors = FXCollections.observableArrayList();

        try {
            authors.addAll(AuthorManager.getAllAuthors());
        } catch (Exception e) {
            e.printStackTrace();
        }

        authorsTable.setItems(authors);
    }


    /**
     * Maneja el clic en el botón para insertar un nuevo autor.
     * Cambia a la vista de inserción de autores.
     */
    public void insertAuthorButtonClicked(){
       cambiarScene("/com/admin/canarysoundsphereadmin/insertAuthor-view.fxml", "Insertar autor", eventsTitle);
    }

    /**
     * Maneja el clic en el botón para actualizar un autor seleccionado.
     * Si hay un autor seleccionado, obtiene su ID y cambia a la vista de actualización de autores.
     * Si no hay autor seleccionado, muestra una alerta de error.
     */
    public void updateAuthorButtonClicked(){
        Author selectedAuthor = authorsTable.getSelectionModel().getSelectedItem();
        if(selectedAuthor != null) {
            authorId = selectedAuthor.get_id();
            cambiarScene("/com/admin/canarysoundsphereadmin/updateAuthor-view.fxml", "Actualizar autor", eventsTitle);
        } else {
            showAlert("Error","Selecciona un autor", Alert.AlertType.ERROR);
        }
    }

    /**
     * Maneja el clic en el botón para eliminar un autor seleccionado.
     * Si hay un autor seleccionado, obtiene su ID y lo elimina.
     * Si la eliminación es exitosa, actualiza la tabla de autores.
     * Si no se puede eliminar, muestra una alerta de error.
     * Si no hay autor seleccionado, muestra una alerta de error.
     */
    public void deleteAuthorButtonClicked(){
        Author selectedAuthor = authorsTable.getSelectionModel().getSelectedItem();
        if(selectedAuthor != null) {
            authorId = selectedAuthor.get_id();
            boolean deleted = deleteAuthorById(authorId, LoginController.token);
            if(deleted) {
                try {
                    ObservableList<Author> authors = FXCollections.observableArrayList();
                    authors.addAll(AuthorManager.getAllAuthors());
                    authorsTable.setItems(authors);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Error","Error al eliminar el autor", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error","Selecciona un autor", Alert.AlertType.ERROR);
        }
    }

    /**
     * Maneja el clic en el botón para insertar un nuevo evento.
     * Cambia a la vista de inserción de eventos.
     */
    public void insertEventButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/insertEvent-view.fxml", "Insertar evento", eventsTitle);
    }

    /**
     * Maneja el clic en el botón para actualizar un evento seleccionado.
     * Si hay un evento seleccionado, obtiene su ID y cambia a la vista de actualización de eventos.
     * Si no hay evento seleccionado, muestra una alerta de error.
     */
    public void updateEventButtonClicked(){
        EventClass selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if(selectedEvent != null) {
            eventId = selectedEvent.get_id();
            cambiarScene("/com/admin/canarysoundsphereadmin/updateEvent-view.fxml", "Actualizar evento", eventsTitle);
        } else {
            showAlert("Error","Selecciona un evento", Alert.AlertType.ERROR);
        }
    }

    /**
     * Maneja el clic en el botón para eliminar un evento seleccionado.
     * Si hay un evento seleccionado, obtiene su ID y lo elimina.
     * Si la eliminación es exitosa, actualiza la tabla de eventos.
     * Si no se puede eliminar, muestra una alerta de error.
     * Si no hay evento seleccionado, muestra una alerta de error.
     */
    public void deleteEventButtonClicked(){
        EventClass selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if(selectedEvent != null) {
            eventId = selectedEvent.get_id();
            boolean deleted = deleteEventById(eventId, LoginController.token);
            if(deleted) {
                try {
                    ObservableList<EventClass> events = FXCollections.observableArrayList();
                    events.addAll(EventManager.getAllEvents());
                    eventsTable.setItems(events);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Error","Error al eliminar el evento", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error","Selecciona un evento", Alert.AlertType.ERROR);
        }
    }

    /**
     * Maneja el clic en el botón para cerrar sesión.
     * Cambia a la vista de inicio de sesión.
     */
    public void close_session(MouseEvent mouseEvent) {
        cambiarScene("/com/admin/canarysoundsphereadmin/login-view.fxml", "Login", eventsTitle);
    }
}