package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import DBHandler.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class HomeController implements Initializable{
    
    @FXML
    private Label welcomeLabel;

    @FXML
    private GridPane serviceGridPane;

    @FXML
    private Label personalInfoLabel;


    Account acct;
    Account acct2;
    DBHandler handler;
    Connection connection;
    Statement stmt;
    ResultSet rs;
    
    public void initialize(URL url, ResourceBundle arg0) {
        handler = new DBHandler();
        //Getting the personal info from LoginController
        acct = LoginController.getInstance().getAccount();
        acct2 = LoginController.getInstance().getAccount2();
        //Saying hello to the user and loading the service chart
        welcomeLabel.setText("Welcome Back " + acct.getName() + " " + acct.getLastname());
        loadServiceGridPane();
    }

    @FXML
    void onAddServiceClick(ActionEvent event) {

    }

    @FXML
    void onShowInfoClick() {
        //Showing personal Info
        personalInfoLabel.setText(acct2.toString());
    }

    private void handleDeleteButton(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Delete Service");
        dialog.setHeaderText("Delete Service " + ((Button)event.getSource()).getId());
        dialog.setContentText("Enter your password");

        Optional<String> result = dialog.showAndWait();

        //This is a Lambda function which is a function without a function name(without method signature) and parameters that don't have an explicit type 
        //password is a parameter but never declared as a String 
        result.ifPresent(password -> System.out.println("password given: " + password));

        String pswdInput = result.get();

        if(pswdInput.isEmpty()) {
            AlertConfigs.missingFieldAlert.showAndWait();
            return;
        }

        boolean isCorrectPassword = checkPassword(pswdInput);

        if(isCorrectPassword)
            deleteFromDB(result.get());

    }

    private void loadServiceGridPane() {
        ArrayList<Service> currS = acct.getServiceList();


        for(int i = 0; i < currS.size(); i++) {

            Button deleteButton = new Button("Delete");
    
            deleteButton.setOnMouseClicked(event -> {
                handleDeleteButton(event);
            });

            deleteButton.setId(currS.get(i).getServiceName());

            Label curr = new Label(currS.get(i).getServiceName());
            curr.setId(currS.get(i).getServiceName());


            serviceGridPane.addRow(i+1, new Label (currS.get(i).getServiceName()), new Label (currS.get(i).getServiceUsername()), new Label (currS.get(i).getServicePassword()), deleteButton);
        }
    }

    private boolean checkPassword(String inputPassword) {
        try {
            connection = handler.getConnection();
            String query = "SELECT user_id FROM credentials WHERE password=\'" + inputPassword + "\'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                if(rs.getString("user_id").equals(acct.getUserid())) {
                    return true;
                }
            }

            if(rs.getString("password").equals(inputPassword))
                return true;

            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            if(e instanceof SQLException) {
                AlertConfigs.cannotDeleteServiceAlert.showAndWait();
                //System.err is like System.out but for errors
                System.err.println("Error in checking password");
                e.printStackTrace();
            }
            else
                AlertConfigs.sqlErrorAlert.showAndWait();
        }

        return false;
    }

    //DB is Database
    private boolean deleteFromDB(String serviceToDelete) {
        try {
            connection = handler.getConnection();
            String query = "DELETE FROM services where service=\'" + serviceToDelete + "\'";
            PreparedStatement p = connection.prepareStatement(query);
            p.executeUpdate();

            p.close();
            connection.close();
        } catch (Exception e) {
            if(e instanceof SQLException) {
                AlertConfigs.sqlErrorAlert.showAndWait();
                //System.err is like System.out but for errors
                System.err.println("Error in deleting service from the database.");
                e.printStackTrace();
            }
            else
                AlertConfigs.unknownErrorAlert.showAndWait();
        }

        return false;
    }
}
