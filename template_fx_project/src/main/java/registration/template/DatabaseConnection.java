package registration.template;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getDBConnection(){
        System.out.println("inside getDBConnection");
        String databaseName = "HW_Airports";
        String databaseUser = "root";
        String databasePassword = "Arrshan_12";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("connection successful to: " + databaseLink);
            
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("❌ Database connection failed:");
        }
        
        return databaseLink;
    }

}
