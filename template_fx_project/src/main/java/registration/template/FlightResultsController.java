package registration.template;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FlightResultsController implements Initializable {

    private Scene scene;
    private Parent root;

    @FXML
    private Button BackButton;

    @FXML 
    private Label arrivalDept;

    @FXML
    private Label dateDisplay;

    @FXML
    private Label DataTimeFLightLabel;

    @FXML
    private VBox flightResultsContainer;

    @FXML 
    private ScrollPane scrollPane;

    @FXML
    private Label LocationDestination;

    private String departureCode;
    private String arrivalCode;

    private String departDate;

    

    public void displayDepartDate(String departureDate) {
        this.departDate = departureDate;
        DataTimeFLightLabel.setText(departureDate);

    }

    public void searchCriteria(String departure, String arrival) {
        System.out.println("Inside searchCriteria. The criteria are: " + departure + " & " + arrival + ".");
        this.departureCode = departure.substring(1, 4);
        this.arrivalCode = arrival.substring(1, 4);
        System.out.println("Filtered codes: " + departureCode + " & " + arrivalCode + ".");
        loadFilteredFlightResults();
    }

    public void backoSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/FlightSearch.fxml"));
        root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void DisplayLocation(String Location){
        System.out.println("Setting LocationDestination to: " + Location);
        if (LocationDestination == null) {
            System.out.println("❌ LocationDestination is null! Check fx:id in FXML.");
        } else {
            LocationDestination.setText(Location);
        }
    }

    private void loadFilteredFlightResults() {
        flightResultsContainer.getChildren().clear();
    
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn1 = dbConn.getDBConnection();
    
        String query = "SELECT flightID, departure, arrival, price, availableSeats FROM HW_Flight_List_T WHERE departure = ? AND arrival = ?";
    
        try (PreparedStatement statement = conn1.prepareStatement(query)) {
            statement.setString(1, departureCode);
            statement.setString(2, arrivalCode);
    
            ResultSet set = statement.executeQuery();
    
            while (set.next()) {
                String flightID = set.getString("flightID");
                String departure = set.getString("departure");
                String arrival = set.getString("arrival");
                String price = set.getString("price");
                int availableSeats = set.getInt("availableSeats");



                

    
                // Placeholder values
                
                String stopInfo = "Connects in Dubai";
                String aircraft = "B777 EK441, A380 EK215";
    
                // Outer VBox for one flight
                VBox flightBox = new VBox(5);
                flightBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-padding: 10;");

                String duration = departDate + " - approx 12 hrs travel"; 
                Label durationLabel = new Label(duration);
                Label routeLabel = new Label("Flight " + flightID + ": " + departure + " ➝ " + arrival);
                Hyperlink stopLink = new Hyperlink(stopInfo);
                Label priceLabel = new Label("Economy class from USD " + price);
                priceLabel.setStyle("-fx-text-fill: green;");
                Label aircraftLabel = new Label("Aircraft: " + aircraft);
                Label seatsLabel = new Label("Seats Available: " + availableSeats);
                ScrollBar scrollBar = new ScrollBar();

                DisplayLocation(routeLabel.getText());
                
    
                Button selectButton = new Button("Select Flight");
                selectButton.setOnAction(e -> {
                    System.out.println("Selected flight: " + flightID);


                    
                  
                });
    
                flightBox.getChildren().addAll(
                    durationLabel, routeLabel, stopLink,
                    priceLabel, aircraftLabel, seatsLabel,
                    selectButton, scrollBar
                );
    
                flightResultsContainer.getChildren().add(flightBox);
    
                if (flightResultsContainer.getChildren().size() == 1) {
                    arrivalDept.setText("Flight " + flightID);
                }
            }
    
            set.close();
            conn1.close();
    
        } catch (Exception e) {
            Logger.getLogger(FlightResultsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // Check if FXML injected properly
    if (flightResultsContainer == null) {
        System.out.println("❌ VBox not injected! Check fx:id or FXML file.");
    } else {
        System.out.println("✅ VBox injected: " + flightResultsContainer);

        if(scrollPane != null) {
            scrollPane.setContent(flightResultsContainer);
            scrollPane.setFitToWidth(true);

        }else{
            System.out.println("❌ ScrollPane not injected! Check fx:id or FXML file.");
        }

        // Add a test flight block
        VBox testFlight = new VBox(5);
        testFlight.setStyle("-fx-border-color: red; -fx-padding: 10;");
        testFlight.getChildren().add(new Label("✅ TEST FLIGHT - this should be visible"));
        flightResultsContainer.getChildren().add(testFlight);
    }

    }
}
