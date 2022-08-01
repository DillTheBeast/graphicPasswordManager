package DBHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHandler {

    private Connection dbConnection;
    

    public Connection getConnection() {
        String connectionString = "jdbc:mysql://" + Configs.dbhost + ":" + Configs.dbport + "/" + Configs.dbname;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Error in DBHandler Class: ");
            e.printStackTrace();
            System.exit(1);
        }
        try {
            dbConnection = DriverManager.getConnection(connectionString, Configs.dbusername, Configs.dbpassword);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in DB Connection");
            alertConfigs.connectionError.showAndWait();
        }
    }
}
