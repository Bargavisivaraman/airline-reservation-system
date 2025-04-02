package registration.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FlightSearchController {

    // If your ComboBox is going to display Strings, you should define that datatype here
    @FXML
    private ComboBox<String> fromDropDown;

    @FXML 
    private ComboBox<String> toDropDown;

    @FXML
    private ComboBox<Integer> noOfPassengersDropDown;

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ToggleGroup choice;

    @FXML
    private RadioButton roundTripRadioButton, oneWayRadioButton;
    
    @FXML
    private DatePicker returnDate, departDate;

    @FXML
    private void initialize() {

        ToggleGroup choice = new ToggleGroup();
        roundTripRadioButton.setToggleGroup(choice);
        oneWayRadioButton.setToggleGroup(choice);

        System.out.println("inside initialize");
        // Within this initialize method, you can initialize the data for the ComboBox. I have changed the
        // method from fillComboBox2() to getData(), which returns a List of Strings.
        // We need to set the ComboBox to use that list.
        ObservableList<String> airportNameList = FXCollections.observableArrayList(getData());
        ObservableList<Integer> noOfPassengers = FXCollections.observableArrayList();
        noOfPassengers.addAll(1, 2, 3, 4, 5, 6, 7 , 8, 9, 10);

        System.out.println(noOfPassengers);
            
        //fromDropDown.setItems(FXCollections.observableArrayList(getData()));
        //toDropDown.setItems(FXCollections.observableArrayList(getData()));
        fromDropDown.setItems(airportNameList);
        toDropDown.setItems(airportNameList);
        noOfPassengersDropDown.setItems(noOfPassengers);
        
        oneWayRadioButton.setOnMouseClicked(event ->  {
            returnDate.setVisible(false);
        });
        roundTripRadioButton.setOnMouseClicked(event ->  {
            returnDate.setVisible(true);
        });

    }

    @FXML
    private void choice(ActionEvent event){
        if(roundTripRadioButton.isSelected()){
            oneWayRadioButton.isSelected(false);
        }
    }

    public void searchFlights(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AvailableFligts.fxml"));
        root = loader.load();

        AvailableFlightsController flightPage = loader.getController();
        flightPage.displayHello();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.setTitle("Available Flights");
        stage.show();
    }

    /*public void oneWaytrip(ActionEvent event) throws IOException {
        DatePicker source = (DatePicker) event.getSource();
        source.setVisible(false);
    }*/



    /**
     * Here we will define the method that builds the List used by the ComboBox
     */
    private List<String> getData() {
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn1 = dbConn.getDBConnection();
        PreparedStatement statement;
    
        // Define the data you will be returning, in this case, a List of Strings for the ComboBox
        List<String> options = new ArrayList<>();

        System.out.println("line 39");
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //Connection con = DriverManager.getConnection(connectionUrl, databaseUser, databasePassword);
            
            String query = "select airportName FROM HW_Airport_List_T";
             statement = conn1.prepareStatement(query);

            ResultSet set = statement.executeQuery();

            while (set.next()) {
                options.add(set.getString("airportName"));
            }
            System.out.println("line 51");
            set.close();
            statement.close();
            conn1.close();
            // Return the List
            return options;

        } catch (Exception e) {
            Logger.getLogger(FlightSearchController.class.getName()).log(Level.SEVERE, null, e);
        }
        return options;
    }
    
}


    
   
   
   
   
   



    
   
   
   
   
   

