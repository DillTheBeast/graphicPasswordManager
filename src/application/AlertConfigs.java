package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertConfigs {
    public static Alert sqlErrorAlert = new Alert(AlertType.ERROR, "Unable to connect to database");

    public static Alert unknownErrorAlert = new Alert(AlertType.ERROR, "There is an unknown error");

    public static Alert invalidCredentialsAlert = new Alert(AlertType.ERROR, "The username and password that were given are invalid");
}
