package sample;

import client.ClientAPI;
import client.ClientImplSingleton;
import entities.Event;
import entities.EventType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    public TableView<Event> eventTable;
    public TableColumn<Event, String> nameTableCol;
    public TableColumn<Event, EventType> typeTableCol;
    public TableColumn<Event, LocalDateTime> startdateTableCol;
    public TableColumn<Event, LocalDateTime> enddateTableCol;


    private ClientAPI clientFunctionality;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientFunctionality = ClientImplSingleton.getInstance();
        initEventTable();
    }


    private void initEventTable() {

        nameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTableCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startdateTableCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        enddateTableCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        eventTable.getItems().setAll(getEvents());
    }

    private ObservableList<Event> getEvents() {
        List<Event> events = clientFunctionality.getAll();
        for (Event event : events) {
            System.out.println(event);
        }
        ObservableList<Event> eventData = FXCollections.observableArrayList();
        eventData.setAll(events);
        for (Event eventDatum : eventData) {
            System.out.println(eventDatum);
        }
        return eventData;
    }
}
