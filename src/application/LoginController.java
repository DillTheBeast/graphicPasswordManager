package application;

import java.io.IOException;
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
    Parent root2;
    Stage stage;
    Stage stage2;
    Scene scene;
    Scene scene2;

    private final String HOME_SCREEN_PATH = "/fxml/home.fxml";
    private final String SIGNUP_SCREEN_PATH = "/fxml/signup.fxml";

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
            switchScene(event, HOME_SCREEN_PATH, "/css/homeapplication.css");
        }
    }

    @FXML
    void onSignUpClick(ActionEvent event) throws IOException {
        switchScene(event, SIGNUP_SCREEN_PATH, "/css/signupapplication.css");
    }
    
    void switchScene(ActionEvent event, String path, String styleSheetPath) {
        try {
            //Transfering to the next Screen

            root = FXMLLoader.load(this.getClass().getResource(path));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(styleSheetPath).toExternalForm());
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

            String query = "SELECT * FROM ACCOUNTS WHERE accounts.user_id =\'" + acct.getUserid() + "\'";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();

            acct = new Account(rs.getString("user_id"), rs.getString("name"), rs.getString("last_name"), rs.getString("address"), rs.getString("state"), rs.getString("email"), rs.getString("phone_number"));
            
            String query2 = "SELECT * FROM Services WHERE services.user_id =\'" + acct.getUserid() + "\'";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query2);

            while(rs.next()) {
                acct.addService(new Service
                (
                    rs.getString("service"), 
                    rs.getString("password"), 
                    rs.getString("username")
                ));
            }

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