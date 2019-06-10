package sample;

import client.ClientAPI;
import client.ClientImplSingleton;
import entities.Event;
import entities.EventType;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    public TableView<Event> eventTable;
    public TableColumn<Event, String> nameTableCol;
    public TableColumn<Event, EventType> typeTableCol;
    public TableColumn<Event, LocalDateTime> startdateTableCol;
    public TableColumn<Event, LocalDateTime> enddateTableCol;
    public TableColumn<Event, String> descriptionTableCol;
    public TableColumn<Event, User> authorTableCol;
    public Label loggedUserLabel;
    public TextField addEventName;
    public TextArea addEventDescription;
    public DatePicker addEventStartDate;
    public DatePicker addEventEndDate;
    public ChoiceBox<EventType> addEventType;
    public Button addEventSubmit;
    public TextField addEventStartDateTimeH;
    public TextField addEventStartDateTimeM;
    public TextField addEventEndDateTimeM;
    public TextField addEventEndDateTimeH;
    public Button deleteEventButton;

    public CheckBox checkMeeting, checkCall, checkBusiness, checkFamily, checkParty, checkOnlyMine;
    public Button filterButton;


    private ClientAPI clientFunctionality;
    private User user;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientFunctionality = ClientImplSingleton.getInstance();
        user = LoginAndRegisterController.loggedUser;
        initEventTable();
        initLoggedUserLabel();

        initMiscellaneous();
    }

    private void initMiscellaneous() {
        addEventType.getItems().setAll(EventType.values());
    }


    public void addEvent() {

        Event e = new Event();
        e.setName(addEventName.getText());
        e.setDescription(addEventDescription.getText());
        e.setStartDate(
                LocalDateTime.of(
                        addEventStartDate.getValue(),
                        LocalTime.of(
                                Integer.valueOf(addEventStartDateTimeH.getText()),
                                Integer.valueOf(addEventStartDateTimeM.getText())
                        )));
        e.setEndDate(
                LocalDateTime.of(
                        addEventEndDate.getValue(),
                        LocalTime.of(
                                Integer.valueOf(addEventEndDateTimeH.getText()),
                                Integer.valueOf(addEventEndDateTimeM.getText())
                        )));
        e.setDescription(addEventDescription.getText());
        e.setType(addEventType.getValue());
        e.setAuthor(user);

        System.out.println(e);
        clientFunctionality.post(e);
        refreshEventTable();

    }

    public void deleteEvent() {
        ObservableList<Event> selectedItems = eventTable.getSelectionModel().getSelectedItems();
        for (Event event : selectedItems) {
            if (event.getAuthor().getId() == user.getId()) {
                clientFunctionality.delete(event.getId());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("NIEPOWODZENIE");
                alert.setHeaderText("Nie mozesz usunac cudzego wydarzenia");
                alert.setContentText("Wybierz inne wydarzenie");
                alert.showAndWait();
            }
        }
        refreshEventTable();


    }

    private void refreshEventTable() {
        eventTable.getItems().setAll(getEvents());
    }


    private void initLoggedUserLabel() {
        loggedUserLabel.setText(user.getFirstName() + " " + user.getLastName());
    }


    private void initEventTable() {

        nameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTableCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startdateTableCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        enddateTableCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        descriptionTableCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        authorTableCol.setCellValueFactory(new PropertyValueFactory<>("author"));
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
