package app;

import client.ClientAPI;
import client.ClientImplSingleton;
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

public class LoginAndRegisterController implements Initializable {

    public TextField loginRegisterTextField;
    public PasswordField passwordRegisterPasswordField;
    public TextField firstNameRegisterTextField;
    public TextField lastNameRegisterTextField;
    public TextField emailRegisterTextField;
    @FXML
    Button loginButton;
    @FXML
    TextField loginTextField;
    @FXML
    PasswordField passwordField;
    private ClientAPI clientFunctionality;
    public static User loggedUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientFunctionality = ClientImplSingleton.getInstance();

    }


    public void loginUser() {
        loggedUser = clientFunctionality.login(loginTextField.getText(), passwordField.getText());

        if (!Objects.isNull(loggedUser)) {
            openMainWindow();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ZLE PASY");
            alert.setHeaderText("PODALES NIEPRAWIDLOWE DANE LOGOWOADNAIDA");
            alert.setContentText("Spróbuj jeszcze raz");
            alert.showAndWait();
        }
    }

    private void openMainWindow() {

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
                emailRegisterTextField.getText(),
                firstNameRegisterTextField.getText(),
                lastNameRegisterTextField.getText());
        boolean register = clientFunctionality.register(user);

        if (register) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUKCES");
            alert.setHeaderText("WITAJ NOWY UZYTKOWNIKU");
            alert.setContentText("Możesz się zalogować w panelu logowania");
            alert.showAndWait();
            Stage stage = (Stage) emailRegisterTextField.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NIEPOWODZENIE");
            alert.setHeaderText("asd");
            alert.setContentText("Spróbuj jeszcze raz");
            alert.showAndWait();
        }
    }
}
