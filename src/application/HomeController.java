package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;
import com.mysql.cj.result.Row;

import DBHandler.DBHandler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
    Parent root;
    Stage stage;
    Scene scene;

    private final String LOGIN_SCREEN_PATH = "/fxml/login.fxml";
    
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
        //Creating custom dialog

        Dialog<Service> dialog = new Dialog<>();
        dialog.setTitle("Add Service");
        dialog.setHeaderText("Add new Service");

// Set the button types.
ButtonType addServiceType = new ButtonType("OK", ButtonData.OK_DONE);
dialog.getDialogPane().getButtonTypes().addAll(addServiceType, ButtonType.CANCEL);

// Create the username and password labels and fields.
GridPane grid = new GridPane();
grid.setHgap(10);
grid.setVgap(10);
grid.setPadding(new Insets(20, 150, 10, 10));

TextField serviceName = new TextField();
serviceName.setPromptText("Ex: Netflix");
TextField serviceUsername = new TextField();
serviceUsername.setPromptText("Username");
PasswordField servicePassword = new PasswordField();

//Adding labels to the grid
grid.add(new Label("Service:"), 0, 0);
grid.add(serviceName, 1, 0);
grid.add(new Label("Username:"), 0, 1);
grid.add(serviceUsername, 1, 1);
grid.add(new Label("Password:"), 0, 2);
grid.add(servicePassword, 1, 2);      


dialog.getDialogPane().setContent(grid);


//Will grab add the values inside the texfields, bring them together into a service object and then return it
dialog.setResultConverter(dialogButton -> {
    if(dialogButton == addServiceType) {
        return new Service(serviceName.getText(), serviceUsername.getText(), servicePassword.getText());
    }
    return null;
});

//Storing the result of the function above
//You need to do this because this can take in something null
Optional<Service> rslt = dialog.showAndWait();

//For debugging purposes
rslt.ifPresent(service -> {
    System.out.println(
        "Username=" + service.getServiceUsername()
        + ", Password=" + service.getServicePassword()
        + ", Service Name=" + service.getServiceName()
    );
});

Service newService = rslt.get();

if(acct.isValidService(newService)) {
    System.out.println("Is a valid service confirmed.");
    boolean addedToDatabase = addToDB(newService);
    System.out.println("Added successfully -> " + addedToDatabase);
    addToServiceGrid(newService);

}
    }

    @FXML
    void onShowInfoClick() {
        //Showing personal Info
        personalInfoLabel.setText(acct2.toString());
    }

    @FXML
    void onSignOutClick(ActionEvent event) throws IOException {
        switchScene(event, LOGIN_SCREEN_PATH, "/css/loginapplication.css");
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
            e.printStackTrace();
            System.out.println("Scene is not able to be switched");
        }
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

        if(isCorrectPassword) {
            deleteFromDB(((Button)event.getSource()).getId());
            deleteFromGrid(((Button)event.getSource()).getId());
        }

    }

    private void loadServiceGridPane() {
        ArrayList<Service> currS = acct.getServiceList();


        for(int i = 0; i < currS.size(); i++) {

            Button deleteButton = new Button("Delete");
    
            deleteButton.setOnMouseClicked(event -> {
                handleDeleteButton(event);
            });

            deleteButton.setId(currS.get(i).getServiceName());

            Label currName = new Label(currS.get(i).getServiceName());
            currName.setId(currS.get(i).getServiceName());


            serviceGridPane.addRow(i+1, currName, new Label (currS.get(i).getServiceUsername()), new Label (currS.get(i).getServicePassword()), deleteButton);
        }
    }

    private boolean checkPassword(String inputPassword) {
        try {
            connection = handler.getConnection();
            //Fix this: should be SELECTYT user_id FROM credentials WHERE username=... and password=...
            String query = "SELECT user_id FROM credentials WHERE password=\'" + inputPassword + "\'";
            //Fix this
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
            System.out.println("Trying to delete service: " + serviceToDelete);
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

    private void deleteFromGrid(String serviceToDelete) {

        //Starting at service name and going up to delete
        for(int i = 0; i < serviceGridPane.getChildren().size(); i++) {
            try {
                Node curr = serviceGridPane.getChildren().get(i);

                //If any of the id's match the id that was passed in
                if(curr.getId().equals(serviceToDelete)) {
                    serviceGridPane.getChildren().remove(i, i + 4);
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private boolean addToDB(Service newService) {
        try {
            System.out.println("... adding to database.");
            connection = handler.getConnection();
            stmt = connection.createStatement();

            //Get current max id
            String query = "SELECT MAX(count) as maxCount FROM services";

            rs = stmt.executeQuery(query);
            rs.next();
            int currentMax = Integer.parseInt(rs.getString("maxCount"));

            System.out.println("Current max count in services: " + currentMax);

            query = "INSERT into services VALUES ("
            + (++currentMax)
            + ", " + acct.getUserid()
            + ", \'" + newService.getServiceName() + "\'"
            + ", \'" + newService.getServiceUsername() + "\'"
            + ", \'" + newService.getServicePassword() + "\'"
            + ");";

            PreparedStatement p = connection.prepareStatement(query);
            p.executeUpdate(query);

            System.out.println("Successfully added to database");

            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            if(e instanceof SQLException) {
            AlertConfigs.sqlErrorAlert.showAndWait();
            System.err.println("Error adding new Service to database");
            e.printStackTrace();
            }
            else
                AlertConfigs.unknownErrorAlert.showAndWait();
        }
        return false;
    }

    private void addToServiceGrid(Service newService) {
        int rowIdx = serviceGridPane.getChildren().size() / 4;

        Button deleteButton = new Button("Delete");

        deleteButton.setOnMouseClicked(event -> {
            handleDeleteButton(event);
        });

        deleteButton.setId(newService.getServiceName());

        Label currServiceName = new Label(newService.getServiceName());
        currServiceName.setId(newService.getServiceName());

        Label currServiceUsername = new Label(newService.getServiceUsername());

        Label currServicePassword = new Label(newService.getServicePassword());

        serviceGridPane.addRow(rowIdx+1, currServiceName, currServiceUsername, currServicePassword, deleteButton);

    }
}
