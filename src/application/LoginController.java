package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.Node;

public class LoginController {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private Label label;

    public void initialize() {

    }
    
    @FXML
    void onLoginClick(ActionEvent event) {
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
}                                                                                                                                                                      
