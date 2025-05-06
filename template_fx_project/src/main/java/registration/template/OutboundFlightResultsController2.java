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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OutboundFlightResultsController2 implements Initializable {

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
    private ComboBox<String> classDropDown;

    @FXML 
    private ScrollPane scrollPane;

    @FXML
    private Label LocationDestination;

    private String departureCode;
    private String arrivalCode;
    private String departDate;
    private String departTime;
    private String arrivalTime;
    private String duration;
    private double price;
    private String returnDate;
    private Integer passengerCount;
    private String storedFirstName;
    

    public void storePassengerCountInfo(Integer count) {
        this.passengerCount = count;
        System.out.println("Passenger count in outbound flight results: " + passengerCount);
    }

    public void setUserName(String firstName) {
        this.storedFirstName = firstName;
    }

    public void storeReturnFlightDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getDepartureCode() {
        return departureCode;
    }

    public String getArrivalCode() {
        return arrivalCode;
    }

    public void DisplayLocation(String Location){
        System.out.println("Setting LocationDestination to: " + Location);
        if (LocationDestination == null) {
            System.out.println("LocationDestination is null! Check fx:id in FXML.");
        } else {
            LocationDestination.setText(Location);
        }
    }

    public void searchCriteria(String departure, String arrival) {
        System.out.println("Inside searchCriteria. The criteria are: " + departure + " & " + arrival + ".");
        this.departureCode = departure.substring(1, 4);
        this.arrivalCode = arrival.substring(1, 4);
        System.out.println("Filtered codes: " + departureCode + " & " + arrivalCode + ".");
        loadFilteredFlightResults();
    }

    public void swapLocations(String departureLocation, String arrivalLocation) {
        this.departureCode = arrivalLocation;
        this.arrivalCode = departureLocation;
        System.out.println("The departure location is now " + departureCode + " and the arrival location is now " + arrivalCode);
        loadFilteredFlightResults();
    }

    public void backToSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/FlightSearch.fxml"));
        root = loader.load();

        FlightSearchController searchPage = loader.getController();
        searchPage.displayUserName(storedFirstName);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private void loadFilteredFlightResults() {

        if (flightResultsContainer.getChildren().size() > 1) {
            flightResultsContainer.getChildren().remove(1, flightResultsContainer.getChildren().size());
        }
    
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn1 = dbConn.getDBConnection();
    
        String query = "SELECT flightID, departure, arrival, price, availableSeats, departureTime, arrivalTime, duration FROM HW_Flight_List_T WHERE departure = ? AND arrival = ?";
    
        try (PreparedStatement statement = conn1.prepareStatement(query)) {
            statement.setString(1, departureCode);
            statement.setString(2, arrivalCode);
    
            ResultSet set = statement.executeQuery();

            boolean hasResults = false;
    
            while (set.next()) {
                hasResults = true;
                String flightID = set.getString("flightID");
                String departure = set.getString("departure");
                String arrival = set.getString("arrival");
                double price = set.getDouble("price");
                int availableSeats = set.getInt("availableSeats");
                String departTime = set.getString("departureTime");
                String arrTime = set.getString("arrivalTime");
                String duration = set.getString("duration");

                double basePrice = price;
                if(classDropDown.getValue().equals("Business Class")) {
                    basePrice += 200;
                } else if (classDropDown.getValue().equals("First Class")) {
                    basePrice += 500;
                }

                // Outer VBox for one flight
                VBox flightBox = new VBox(5);
                flightBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-padding: 10;");

                Label durationLabel = new Label("Total time on flight is: " + duration);
                Label routeLabel = new Label("Flight " + flightID + ": " + departure + " ➝ " + arrival);
                Label departureTimeLabel = new Label("Departure: " + departTime);
                Label arrivalTimeLabel = new Label("Arrival: " + arrTime);
                Label priceLabel = new Label("Economy class from USD " + String.format("%.2f", basePrice));
                priceLabel.setStyle("-fx-text-fill: green;");
                Label seatsLabel = new Label("Seats Available: " + availableSeats);
                Label flightTimeLabel = new Label("Flight Time: " + departTime + " - " + arrTime);
                
                ScrollBar scrollBar = new ScrollBar();

                DisplayLocation(routeLabel.getText());

                final String finalDepartTime = departTime;
                final String finalArrivalTime = arrTime;
                final String finalDuration = duration;
                final double finalPrice = price;
                
    
                Button selectButton = new Button("Select Flight");
                selectButton.setOnAction(e -> {
                    try {
                        System.out.println("Inside goToFlightReview");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/InboundFlightResults.fxml"));
                        root = loader.load();
                        System.out.println("After loading the root");

                        InboundFlightResultsController2 inboundFlightPage = loader.getController();
                        inboundFlightPage.swapLocations(departureCode, arrivalCode);
                        inboundFlightPage.storeOutboundDetails(departureCode, arrivalCode, 
                                                                departDate, finalDepartTime, finalArrivalTime, finalDuration, finalPrice);
                        inboundFlightPage.storePassengerCountInfo(passengerCount);
                        inboundFlightPage.storeReturnFlightDate(returnDate);
                
                        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        Logger.getLogger(OneWayFlightResultsController.class.getName()).log(Level.SEVERE, null, e1);
                    }
                });
    
                flightBox.getChildren().addAll(
                    durationLabel, routeLabel,
                    departureTimeLabel, arrivalTimeLabel,
                    priceLabel, seatsLabel,
                    selectButton, scrollBar
                );

                
    
                flightResultsContainer.getChildren().add(flightBox);
            }

            if (departureCode == null || arrivalCode == null) {

                Label noResults = new Label("⚠ No flights found for the selected route.");
                noResults.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-padding: 10;");
                flightResultsContainer.getChildren().add(noResults);
            }

            if(!hasResults) {
                Label noResults = new Label("No flights found for the selected route.");
                noResults.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-padding: 10;");
                flightResultsContainer.getChildren().add(noResults);
            }
    
            set.close();
            conn1.close();
    
        } catch (Exception e) {
            Logger.getLogger(OneWayFlightResultsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        // Check if FXML injected properly
    if (flightResultsContainer == null) {
        System.out.println("VBox not injected. Check fx:id or FXML file.");
    } else {
        System.out.println("VBox injected: " + flightResultsContainer);

        if (scrollPane != null) {
            scrollPane.setContent(flightResultsContainer);
            scrollPane.setFitToWidth(true);

        } else {
            System.out.println("ScrollPane not injected! Check fx:id or FXML file.");
        }

        // Add a test flight block
        VBox testFlight = new VBox(5);
        testFlight.setStyle("-fx-border-color: red; -fx-padding: 10;");
        testFlight.getChildren().add(new Label("TEST FLIGHT - this should be visible"));
        flightResultsContainer.getChildren().add(testFlight);
    }

        classDropDown = new ComboBox<>();
        classDropDown.getItems().addAll(
            "Economy Class",
            "Business Class",
            "First Class"
        );
        classDropDown.setValue("Economy Class");
        classDropDown.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Dropdown changed from " + oldValue + " to " + newValue);
            loadFilteredFlightResults();
        });

        HBox topSection = new HBox();
        topSection.setPadding(new Insets(10));
        topSection.setAlignment(Pos.CENTER_RIGHT);
        topSection.getChildren().add(classDropDown);

        flightResultsContainer.getChildren().add(0, topSection);
    }
}