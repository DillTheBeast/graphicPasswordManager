package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DBHandler.DBHandler;
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

    DBHandler handler;
    Connection connection;
    Statement stmt;
    ResultSet rs;

    public void initialize() {
        handler = new DBHandler();
    }

    @FXML
    void onEnterClick(ActionEvent event) throws SQLException {
        //See if any fields are empty
        if(areFieldsEmpty()) {
            AlertConfigs.invalidInput.setContentText("There is an empty text field that needs to have text in it");
            AlertConfigs.invalidInput.showAndWait();
        }
        else {
            if(isInvalidUsername()) {
                AlertConfigs.invalidInput.showAndWait();
            }
            else{
                System.out.println("Hi");
            }
        }
    }

    public boolean areFieldsEmpty() {
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
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isInvalidUsername() throws SQLException {
        if(usernameField.getText().length() < 5) {
            AlertConfigs.invalidInput.setContentText("The username given is too small. It needs to be five or more letters");
            return true;
        }

        boolean isInvalidUsername = true;
        connection = handler.getConnection();

        String query = "SELECT * FROM password_manager.credentials WHERE username=\'"  + usernameField.getText() + "\'";

        stmt = connection.createStatement();
        rs = stmt.executeQuery(query);

            if(!rs.next()) {
                AlertConfigs.invalidInput.setContentText("This username is already taken. Please select a different username.");
                AlertConfigs.invalidInput.showAndWait();
                isInvalidUsername = false;
            }
            else {

            }
        
        rs.close();
        stmt.close();
        connection.close();

        return isInvalidUsername;
    }
}