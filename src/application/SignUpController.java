package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

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
    private TextField passwordConfirmationField;

    @FXML
    private TextField phoneNumberField;

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
        if(
            isValidUsername()
            && isValidPassword()
        ) {
            
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
        || phoneNumberField.getText().equals("")) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isValidUsername() throws SQLException {
        if(usernameField.getText().length() < 5) {
            AlertConfigs.invalidInput.setContentText("The username given is too small. It needs to be five or more letters");
            AlertConfigs.invalidInput.showAndWait();
            return false;
        }

        boolean isValidUsername = false;
        connection = handler.getConnection();

        String query = "SELECT * FROM password_manager.credentials WHERE username=\'"  + usernameField.getText() + "\'";

        stmt = connection.createStatement();
        rs = stmt.executeQuery(query);

            if(rs.next()) {
                AlertConfigs.invalidInput.setContentText("This username is already taken. Please select a different username.");
                AlertConfigs.invalidInput.showAndWait();
            }
            
            else 
                isValidUsername = true;
            
        
        rs.close();
        stmt.close();
        connection.close();

        return isValidUsername;
    }

    public boolean isValidPassword() {
        if(!(passwordField.getText().equals(passwordConfirmationField.getText()))) {
            AlertConfigs.invalidInput.setContentText("The password that you have entered is not the same as the password you entered for the confirmation password.");
            AlertConfigs.invalidInput.showAndWait();
            return false;
        }

        if(!(isStrongPassword(passwordField.getText()))) {
            AlertConfigs.notStrongPassword.showAndWait();
            return false;
        }

        return true;
    }

    private boolean isStrongPassword(String password) {
        //Checking if the password has a number, has a lowercase letter, has an uppercase letter, has a special character, and is somewhere between 8 and 20 characters
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[!@#$%^&+=])" + "(?=\\S+$).{8,20}$";

        //Sets the String version of the pattern to the object version of the pattern
        Pattern p = Pattern.compile(regex);

        return p.matches(regex, password);
    }

    public boolean isValidNames() {
        if(!(containsNumbers(firstNameField.getText()))) {
            AlertConfigs.invalidInput.setContentText("The first name field should only contain letters");
            AlertConfigs.invalidInput.showAndWait();
            return false;
        }

        if(!(containsNumbers(lastNameField.getText()))) {
            AlertConfigs.invalidInput.setContentText("The first name field should only contain letters");
            AlertConfigs.invalidInput.showAndWait();
            return false;
        }

        if(!(containsNumbers(stateField.getText()))) {
            AlertConfigs.invalidInput.setContentText("The first name field should only contain letters");
            AlertConfigs.invalidInput.showAndWait();
            return false;
        }
        
        return true;
    }

    //Checking to find if there is a number in whatever is referenced
    public boolean containsNumbers(String word) {
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);
        return p.matches(regex, word);
    }

}