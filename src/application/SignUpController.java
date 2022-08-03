package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private Label label;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField serviceField;

    @FXML
    private TextField servicePasswordFIeld;

    @FXML
    private TextField serviceUsernameField;

    @FXML
    private TextField stateField;

    @FXML
    private TextField usernameField;

    public void initialize() {

    }

    @FXML
    void onEnterClick(ActionEvent event) {
        if(usernameField.getText().equals("") 
        || passwordField.getText().equals("") 
        || firstNameField.getText().equals("") 
        || lastNameField.getText().equals("") 
        || addressField.getText().equals("")
        || stateField.getText().equals("")
        || emailField.getText().equals("")
        || phoneNumberField.getText().equals("")
        || serviceField.getText().equals("")
        || servicePasswordFIeld.getText().equals("")
        || serviceUsernameField.getText().equals("")) {
            System.out.println("Hi");
        }
        else {
            System.out.println("Test");
        }
    }
}