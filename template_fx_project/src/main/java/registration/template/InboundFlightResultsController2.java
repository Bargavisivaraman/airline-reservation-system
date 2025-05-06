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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InboundFlightResultsController2 implements Initializable {

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

    @FXML
    private ComboBox<String> classDropDown;

    private String departureCode;
    private String arrivalCode;

    private String outDepartDate;
    private String outDepLocation;
    private String outArrLocation;
    private String outDepTime;
    private String outArrTime;
    private String outDuration;
    private double outPrice;

    private String inDepartDate;
    private String inDepLocation;
    private String inArrLocation;
    private String inDepTime;
    private String inArrTime;
    private String inDuration;
    private double inPrice;

    private Integer passengerCount;


    
    public void swapLocations(String departureLocation, String arrivalLocation) {
        this.inDepLocation = arrivalLocation;
        this.inArrLocation = departureLocation;
        System.out.println("The departure location is now " + inDepLocation + " and the arrival location is now " + inArrLocation);
        loadFilteredFlightResults();
    }

    public void storePassengerCountInfo(Integer count) {
        this.passengerCount = count;
        System.out.println("Passenger count in inbound flight results: " + passengerCount);
    }

    public void storeOutboundDetails (String departureLocation, String arrivalLocation, 
                                      String departDate, String depTime, String arrTime, 
                                      String duration, double price) {
        this.outDepartDate = departDate;
        this.outDepLocation =  departureLocation;
        this.outArrLocation = arrivalLocation;
        this.outDepTime = depTime;
        this.outArrTime = arrTime;
        this.outDuration = duration;
        this.outPrice = price;
    }

    public void storeReturnFlightDate(String returnDate) {
        this.inDepartDate = returnDate;
    }


    public void backToOutbound(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/OutboundFlightResults.fxml"));
        root = loader.load();

        OutboundFlightResultsController2 resultsPage = loader.getController();
        resultsPage.swapLocations(inDepLocation, inArrLocation);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void DisplayLocation(String Location){
        System.out.println("Setting LocationDestination to: " + Location);
        if (LocationDestination == null) {
            System.out.println("LocationDestination is null! Check fx:id in FXML.");
        } else {
            LocationDestination.setText(Location);
        }
    }
    

    private void loadFilteredFlightResults() {
        System.out.println("Inside loadFilteredFlightResults");

        if (flightResultsContainer.getChildren().size() > 1) {
            flightResultsContainer.getChildren().remove(1, flightResultsContainer.getChildren().size());
        }
    
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn1 = dbConn.getDBConnection();
    
        String query = "SELECT * FROM HW_Flight_List_T WHERE departure = ? AND arrival = ?";
    
        try (PreparedStatement statement = conn1.prepareStatement(query)) {
            statement.setString(1, inDepLocation);
            statement.setString(2, inArrLocation);
    
            ResultSet set = statement.executeQuery();

            System.out.println("Query executed: " + query);
    
            while (set.next()) {
                String flightID = set.getString("flightID");
                String departure = set.getString("departure");
                String arrival = set.getString("arrival");
                double price = set.getDouble("price");
                int availableSeats = set.getInt("availableSeats");
                String departTime = set.getString("departureTime");
                String arrTime = set.getString("arrivalTime");
                String duration = set.getString("duration");

                System.out.println(departure);

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

                final String finalDepartLocation = inDepLocation;
                final String finalArrLocation = inArrLocation;
                final String finalDepartTime = departTime;
                final String finalArrTime = arrTime;
                final String finalDuration = duration;
                final double finalPrice = price;
                final String finalDepartDate = inDepartDate;
                
    
                Button selectButton = new Button("Select Flight");
                selectButton.setOnAction(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/RoundTripFlightReview2.fxml"));
                        Parent root = loader.load();
                    
                        System.out.println("After loading the root");

                        this.inDepLocation = finalDepartLocation;
                        this.inArrLocation = finalArrLocation;
                        this.inDepTime = finalDepartTime;
                        this.inArrTime = finalArrTime;
                        this.inDuration = finalDuration;
                        this.inPrice = finalPrice;
                        this.inDepartDate = finalDepartDate;

                        RoundTripFlightReviewController2 flightReviewPage = loader.getController();
                        flightReviewPage.setOutboundFlightDetails(outDepLocation, outArrLocation, outDepartDate, outDepTime, outArrTime, 
                                                                  outDuration, outPrice);
                        flightReviewPage.setInboundFlightDetails(inDepLocation, inArrLocation, inDepartDate, inDepTime, inArrTime, 
                                                                  inDuration, inPrice);
                        flightReviewPage.storeReturnFlightDate(inDepartDate);
                        flightReviewPage.storePassengerCountInfo(passengerCount);

                        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setMaximized(true);
                        stage.show();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        Logger.getLogger(InboundFlightResultsController2.class.getName()).log(Level.SEVERE, null, e1);
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
    
            set.close();
            conn1.close();
    
        } catch (Exception e) {
            Logger.getLogger(InboundFlightResultsController2.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // Check if FXML injected properly
    if (flightResultsContainer == null) {
        System.out.println("VBox not injected! Check fx:id or FXML file.");
    } else {
        System.out.println("VBox injected: " + flightResultsContainer);

        if(scrollPane != null) {
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