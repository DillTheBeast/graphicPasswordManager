package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class HomeController implements Initializable{
    
    @FXML
    private Label welcomeLabel;

    @FXML
    private GridPane serviceGridPane;



    Account acct;
    
    public void initialize(URL url, ResourceBundle arg0) {
        acct = LoginController.getInstance().getAccount();
        welcomeLabel.setText("Welcome Back " + acct.getName() + " " + acct.getLastname());
        loadServiceGridPane();
    }

    @FXML
    void onAddRowClick(ActionEvent event) {

    }

    private void handleDeleteButton() {

    }

    private void handleEditButton() {

    }

    private void loadServiceGridPane() {
        ArrayList<Service> currS = acct.getServiceList();


        for(int i = 0; i < currS.size(); i++) {

            Button editButton = new Button("Edit");

            editButton.setOnMouseClicked(event -> {
                handleEditButton();
            });
    
            Button deleteButton = new Button("Delete");
    
            deleteButton.setOnMouseClicked(event -> {
                handleDeleteButton();
            });

            Label curr = new Label(currS.get(i).getServiceName());


            serviceGridPane.addRow(i+1, new Label (currS.get(i).getServiceName()), new Button(), new Button());
        }
    }

}
