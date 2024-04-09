package com.admin.canarysoundsphereadmin.controllers;

import com.admin.canarysoundsphereadmin.models.DBManager;
import com.admin.canarysoundsphereadmin.models.EventClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONException;

import java.io.IOException;

import static com.admin.canarysoundsphereadmin.models.DBManager.getAllEvents;

public class TablesController {

    @FXML
    private TableView<EventClass> eventsTable;

    @FXML
    private TableColumn<EventClass, String> idColumn;

    @FXML
    private TableColumn<EventClass, String> logoColumn;

    @FXML
    private TableColumn<EventClass, String> imageColumn;

    @FXML
    private TableColumn<EventClass, String> nameColumn;

    @FXML
    private TableColumn<EventClass, String> dateColumn;

    @FXML
    private TableColumn<EventClass, String> timeColumn;

    @FXML
    private TableColumn<EventClass, Integer> capacityColumn;

    @FXML
    private TableColumn<EventClass, String> descriptionColumn;

    @FXML
    private TableColumn<EventClass, String> directionColumn;

    @FXML
    private TableColumn<EventClass, String> markerColumn;

    @FXML
    private TableColumn<EventClass, String> ticket_storeColumn;

    @FXML
    protected void initialize() throws JSONException, IOException {
        System.out.println(getAllEvents().get(0).get_id());
// Configura las columnas para que sepan qué campo de Event deben mostrar
        idColumn.setCellValueFactory(new PropertyValueFactory<>("_id"));
        logoColumn.setCellValueFactory(new PropertyValueFactory<>("logo"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        directionColumn.setCellValueFactory(new PropertyValueFactory<>("direction"));
        markerColumn.setCellValueFactory(new PropertyValueFactory<>("marker"));
        ticket_storeColumn.setCellValueFactory(new PropertyValueFactory<>("ticket_store"));

        ObservableList<EventClass> events = FXCollections.observableArrayList();
        try {
            events.addAll(DBManager.getAllEvents());
        } catch (Exception e) {
            e.printStackTrace();
        }



        // Asigna la lista de eventos a la tabla
        eventsTable.setItems(events);
    }
}