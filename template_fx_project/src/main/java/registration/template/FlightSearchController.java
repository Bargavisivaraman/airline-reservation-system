package registration.template;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlightSearchController {

    // If you ComboBox is going to display Strings, you should define that datatype here
    @FXML
    private ComboBox<String> comboBoxx;

    @FXML
    private void initialize() {

        System.out.println("inside initialize");
        // Within this initialize method, you can initialize the data for the ComboBox. I have changed the
        // method from fillComboBox2() to getData(), which returns a List of Strings.
        // We need to set the ComboBox to use that list.
        comboBoxx.setItems(FXCollections.observableArrayList(getData()));

    }

    /**
     * Here we will define the method that builds the List used by the ComboBox
     */
    private List<String> getData() {
        DatabaseConnection bds = new DatabaseConnection();
        Connection conn1 = bds.getDBConnection();
       /* System.out.println("inside getData");
        String connectionUrl = "jdbc:mysql://localhost:3306/HW_Airports?useSSL=false";
        //String databaseName = "HW_Airports";
        String databaseUser = "root";
        String databasePassword = "Arrshan_12";
        */
        

        // Define the data you will be returning, in this case, a List of Strings for the ComboBox
        List<String> options = new ArrayList<>();

       

        System.out.println("line 39");
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //Connection con = DriverManager.getConnection(connectionUrl, databaseUser, databasePassword);
            
            String query = "select airportName FROM HW_Airport_List_T";
            PreparedStatement statement = conn1.prepareStatement(query);

            ResultSet set = statement.executeQuery();

            while (set.next()) {
                options.add(set.getString("airportName"));
            }
            System.out.println("line 51");
            statement.close();
            set.close();

            // Return the List
            return options;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FlightSearchController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
       
    /*@Override
    public void initialize (URL url, ResourceBundle resource) {

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getDBConnection();

    String flightViewQuery = "Airport_code, airportName FROM HW_Airport_List_T";
    
    try {

        Statement statement = connectDB.createStatement();
        ResultSet queryOutput = statement.executeQuery(flightViewQuery);
        while(queryOutput.next()) {
            
        }
    } catch (SQLException e) {
        System.err.println(FlightSearchController.class.getName());
    
    }
    }*/
}


    
   
   
   
   
   

