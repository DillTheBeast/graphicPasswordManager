package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertConfigs {
    public static Alert sqlErrorAlert = new Alert(AlertType.ERROR, "Unable to connect to database");

    public static Alert unknownErrorAlert = new Alert(AlertType.ERROR, "There is an unknown error");

    public static Alert invalidCredentialsAlert = new Alert(AlertType.ERROR, "The username and password that were given are invalid");

    public static Alert notStrongPassword = new Alert(AlertType.ERROR, "The password entered is too weak. It needs to have the following: " 
    + "\n Between 8 and 20 characters,"
    + "\n At least 1 lowercase letter,"
    + "\n At least 1 uppercase letter," 
    + "\n At least 1 special character,"
    + "\n and at least 1 number");

    //Invalid input manages most of the errors and when you need to use it you just reset the text right before calling it
    public static Alert invalidInput = new Alert(AlertType.ERROR, "");

    public static Alert cannotDeleteServiceAlert = new Alert(AlertType.ERROR, "Cannot delete service. Please check your internet connection.");

    public static Alert missingFieldAlert = new Alert(AlertType.ERROR, "Error. Invalid input. You are missing a field.");
}
