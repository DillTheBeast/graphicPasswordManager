package application;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import DBHandler.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;

public class LoginController {

    Parent root;
    Stage stage;
    Scene scene;

    DBHandler handler;
    Connection connection;
    Statement stmt;
    ResultSet rs;
    
    Account acct;

    private static LoginController instance;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;


    public void initialize() {

    }
    
    public LoginController() {
        //Type variable name = new Type();
        handler = new DBHandler();
        instance = this;
    }

    @FXML
    void onLoginClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(isValidCredentials(username, password)) {
            initializeAccount();
            switchScene(event);
        }
    }
    
    void switchScene(ActionEvent event) {
        try {
            //Transfering to the next Screen
            root = FXMLLoader.load(this.getClass().getResource("/fxml/home.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("Scene is not able to be switched");
        }
    }

    //username and password = place holders for usernameField and passwordField
    boolean isValidCredentials(String username, String password) {

        try {
            connection = handler.getConnection();

            String query = "SELECT user_id FROM credentials WHERE username=\'" + username + "\' AND password=\'" + password + "\'";


            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            
            if(!rs.next()) { 
                AlertConfigs.invalidCredentialsAlert.showAndWait();
                return false;
            }

            acct = new Account();
            acct.setUserid(rs.getString("user_id"));

            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void initializeAccount() {
        try {
            connection = handler.getConnection();

            String query = "SELECT * FROM password_manager.accounts WHERE user_id = \'" + acct.getUserid() + "\'";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();

            acct = new Account(rs.getString("user_id"), rs.getString("name"), rs.getString("last_name"), rs.getString("address"), rs.getString("state"), rs.getString("email"), rs.getString("phone_number"));

            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LoginController getInstance() {
        return instance;
    }

    public Account getAccount() {
        return this.acct;
    }

}                                                                                                                                                                      