package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomeController implements Initializable{
    
    @FXML
    private Label label;

    Account acct;
    
    public void initialize(URL url, ResourceBundle arg0) {
        acct = LoginController.getInstance().getAccount();
        label.setText(acct.toString());
        System.out.println(acct.toString());
        System.out.println("Hello");
    }
}
