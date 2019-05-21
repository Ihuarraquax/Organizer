package sample;

import client.ClientAPI;
import client.ClientImpl;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField loginRegisterTextField;
    public PasswordField passwordRegisterPasswordField;
    public TextField firstNameRegisterTextField;
    public TextField lastNameRegisterTextField;
    @FXML
    Button loginButton;
    @FXML
    TextField loginTextField;
    @FXML
    PasswordField passwordField;
    private ClientAPI clientFunctionality;
    private User loggedUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientFunctionality = new ClientImpl();
    }


    public void loginUser() {
        loggedUser = clientFunctionality.login(loginTextField.getText(), passwordField.getText());

        if(!Objects.isNull(loggedUser)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUKCES");
            alert.setHeaderText("WITAJ "+loggedUser.getFirstName()+"!");
            alert.setContentText("elelel");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ZLE PASY");
            alert.setHeaderText("PODALES NIEPRAWIDLOWE DANE LOGOWOADNAIDA");
            alert.setContentText("Spróbuj jeszcze raz");
            alert.showAndWait();
        }
    }

    public void openRegisterWindow(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("registerView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void register() {
        User user = new User(
                loginRegisterTextField.getText(),
                passwordRegisterPasswordField.getText(),
                firstNameRegisterTextField.getText(),
                lastNameRegisterTextField.getText());
        boolean register = clientFunctionality.register(user);

        if(register) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUKCES");
            alert.setHeaderText("WITAJ NOWY UZYTKOWNIKU");
            alert.setContentText("Możesz się zalogować w panelu logowania");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NIEPOWODZENIE");
            alert.setHeaderText("asd");
            alert.setContentText("Spróbuj jeszcze raz");
            alert.showAndWait();
        }
    }
}
