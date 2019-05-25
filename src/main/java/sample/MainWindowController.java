package sample;

import client.ClientAPI;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    public TableView eventTable;
    private ClientAPI clientFunctionality;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initEventTable();
    }


    private void initEventTable() {

    }

    public void setClientFunctionality(ClientAPI clientFunctionality) {
        this.clientFunctionality = clientFunctionality;
    }
}
