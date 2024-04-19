package com.admin.canarysoundsphereadmin.controllers;

import com.admin.canarysoundsphereadmin.models.Author;
import com.admin.canarysoundsphereadmin.models.DBManager;
import com.admin.canarysoundsphereadmin.models.EventClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONException;

import java.io.IOException;

import static com.admin.canarysoundsphereadmin.controllers.MethodsForControllers.cambiarScene;
import static com.admin.canarysoundsphereadmin.models.DBManager.*;

public class TablesController {
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

    public static String eventId;

    public static String authorId;

    @FXML
    protected void initialize() throws JSONException, IOException {
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
            events.addAll(DBManager.getAllEvents());
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
            authors.addAll(DBManager.getAllAuthors());
        } catch (Exception e) {
            e.printStackTrace();
        }

        authorsTable.setItems(authors);
    }

    public void insertAuthorButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/insertAuthor-view.fxml", "Insertar evento", eventsTitle);
    }

    public void updateAuthorButtonClicked(){
        Author selectedAuthor = authorsTable.getSelectionModel().getSelectedItem();

        if(selectedAuthor != null) {
            authorId = selectedAuthor.get_id();
            cambiarScene("/com/admin/canarysoundsphereadmin/updateAuthor-view.fxml", "Actualizar autor", eventsTitle);
        } else {
            System.out.println("Por favor, selecciona un evento para eliminar.");
        }
    }

    public void deleteAuthorButtonClicked(){
        Author selectedAuthor = authorsTable.getSelectionModel().getSelectedItem();

        if(selectedAuthor != null) {
            authorId = selectedAuthor.get_id();
            boolean deleted = deleteAuthorById(authorId);
            if(deleted) {
                try {
                    ObservableList<Author> authors = FXCollections.observableArrayList();
                    authors.addAll(DBManager.getAllAuthors());
                    authorsTable.setItems(authors);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error al eliminar el evento.");
            }
        } else {
            System.out.println("Por favor, selecciona un evento para eliminar.");
        }
    }

    public void insertEventButtonClicked(){
        cambiarScene("/com/admin/canarysoundsphereadmin/insertEvent-view.fxml", "Insertar evento", eventsTitle);
    }

    public void updateEventButtonClicked(){
        EventClass selectedEvent = eventsTable.getSelectionModel().getSelectedItem();

        if(selectedEvent != null) {
            eventId = selectedEvent.get_id();
            cambiarScene("/com/admin/canarysoundsphereadmin/updateEvent-view.fxml", "Actualizar evento", eventsTitle);

        } else {
            System.out.println("Por favor, selecciona un evento para eliminar.");
        }
    }

    public void deleteEventButtonClicked(){
        EventClass selectedEvent = eventsTable.getSelectionModel().getSelectedItem();

        if(selectedEvent != null) {
            eventId = selectedEvent.get_id();
            boolean deleted = deleteEventById(eventId);
            if(deleted) {
                try {
                    ObservableList<EventClass> events = FXCollections.observableArrayList();
                    events.addAll(DBManager.getAllEvents());
                    eventsTable.setItems(events);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error al eliminar el evento.");
            }
        } else {
            System.out.println("Por favor, selecciona un evento para eliminar.");
        }
    }

}