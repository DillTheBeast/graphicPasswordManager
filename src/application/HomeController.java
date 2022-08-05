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

    @FXML
    private Label personalInfoLabel;


    Account acct;
    Account acct2;
    
    public void initialize(URL url, ResourceBundle arg0) {
        acct = LoginController.getInstance().getAccount();
        acct2 = LoginController.getInstance().getAccount2();
        welcomeLabel.setText("Welcome Back " + acct.getName() + " " + acct.getLastname());
        loadServiceGridPane();
    }

    @FXML
    void onAddRowClick(ActionEvent event) {

    }

    @FXML
    void onShowInfoClick() {
        personalInfoLabel.setText(acct2.toString());
    }

    private void handleDeleteButton() {

    }

    private void loadServiceGridPane() {
        ArrayList<Service> currS = acct.getServiceList();


        for(int i = 0; i < currS.size(); i++) {

            Button deleteButton = new Button("Delete");
    
            deleteButton.setOnMouseClicked(event -> {
                handleDeleteButton();
            });

            Button showButton = new Button("Show");

            Label curr = new Label(currS.get(i).getServiceName());
            curr.setId(currS.get(i).getServiceName());


            serviceGridPane.addRow(i+1, new Label (currS.get(i).getServiceName()), new Label (currS.get(i).getServiceUsername()), new Label (currS.get(i).getServicePassword()), deleteButton);
        }
    }

}
