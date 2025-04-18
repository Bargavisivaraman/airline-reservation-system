package registration.template;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import javafx.scene.control.*;

public class FlightResultsController implements Initializable {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Label dateDisplay;

    @FXML
    private TableView<AvailableFlightsModel> flightTable;

    @FXML
    private TableColumn<AvailableFlightsModel, String> flightIDColumn;

    @FXML
    private TableColumn<AvailableFlightsModel, String> departureColumn; 

    @FXML
    private TableColumn<AvailableFlightsModel, String> arrivalColumn;

    @FXML
    private TableColumn<AvailableFlightsModel, String> priceColumn;

    @FXML
    private TableColumn<AvailableFlightsModel, Integer> availableSeatsColumn;

    ObservableList<AvailableFlightsModel> flightResults;

    private String departureCode;
    private String arrivalCode;

    public void displayDepartDate(String departureDate) {
        dateDisplay.setText(departureDate);
    }

    public void searchCriteria(String departure, String arrival) {
        System.out.println("Inside searchCritera. The criterias are: " + departure + " & " + arrival +  ".");
        this.departureCode = departure.substring(1, 4);
        this.arrivalCode = arrival.substring(1, 4);
        System.out.println("Inside searchCritera. The criterias are: " + departureCode + " & " + arrivalCode +  ".");
        flightTable.setItems(loadFilteredFlightResults());
    }

    public void backToSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/FlightSearch.fxml"));
        root = loader.load();

        //FlightSearchController flightResultsPage = loader.getController();
        //flightResultsPage.displayPreviousUserChoices(this.departureCode, this.arrivalCode, LocalDate.parse(dateDisplay.getText()));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    ObservableList<AvailableFlightsModel> loadFilteredFlightResults() {
        System.out.println("Inside loadFilteredFlightResults");
        ObservableList<AvailableFlightsModel> flightResults = FXCollections.observableArrayList();
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn1 = dbConn.getDBConnection();
        PreparedStatement statement;

        String availableFlightsQuery = "SELECT flightID, departure, arrival, price, availableSeats " + "FROM HW_Flight_List_T WHERE departure = ? AND arrival = ?";

        try {
            statement = conn1.prepareStatement(availableFlightsQuery);
            statement.setString(1, departureCode);
            statement.setString(2, arrivalCode);

            ResultSet set = statement.executeQuery();

            while (set.next()) {
                String flightID = set.getString("flightID");
                String departure = set.getString("departure");
                String arrival = set.getString("arrival");
                String price = set.getString("price");
                Integer availableSeats = set.getInt("availableSeats");

                flightResults.add(new AvailableFlightsModel(flightID, departure, arrival, price, availableSeats));
            }

            set.close();
            statement.close();
            conn1.close();

        } catch (Exception SQLException) {
            Logger.getLogger(FlightResultsController.class.getName()).log(Level.SEVERE, null, SQLException); 
        }
        return flightResults;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        flightIDColumn.setCellValueFactory(new PropertyValueFactory<AvailableFlightsModel, String >("flightID"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<AvailableFlightsModel, String>("departure"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<AvailableFlightsModel, String>("arrival"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<AvailableFlightsModel, String>("price"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<AvailableFlightsModel, Integer>("availableSeats")); 

    }

}