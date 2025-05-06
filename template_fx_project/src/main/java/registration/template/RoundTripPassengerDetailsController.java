package registration.template;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class RoundTripPassengerDetailsController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox passengerBox, flightInfoBox;;

    @FXML
    private Label flightPlanText, flightTotalCost;


    private String departureCode;
    private String arrivalCode;
    private String departDate;
    private String departTime;
    private String arrivalTime;
    private String duration;
    private double price;

    private final List<TextField[]> passengerInputFields = new ArrayList<>();

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        System.out.println("Number of passengers in round trip passenger details: " + numberOfPassengers);
        populateEntryFields(numberOfPassengers);
    }

    @FXML
    private void backToFlightResults(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/OutboundFlightResults.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.setTitle("Flight Results");
        stage.show(); // show the screen
        
    }

    @FXML
    private void bookFlight(ActionEvent event) throws IOException {

        List<Passenger> passengerList = new ArrayList<>();

        for (TextField[] fields : passengerInputFields) {
            String firstName = fields[0].getText().trim();
            String lastName = fields[1].getText().trim();

            if (!firstName.isEmpty() && !lastName.isEmpty()) {
                passengerList.add(new Passenger(firstName, lastName));
            }
        }

        String bookingId = "SPK" + (int)(Math.random() * 1_000_000);

        Booking booking = new Booking(
            bookingId,
            departureCode,
            arrivalCode,
            departDate,
            departTime,
            arrivalTime,
            duration,
            price,
            passengerList
        );

        UserSession.addBooking(booking);
        System.out.println("Flight booked! Booking ID: " + bookingId);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/account.fxml"));
        Parent root = loader.load();

        Account accountController = loader.getController();
        accountController.setUserName(UserSession.getFirstName());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void populateEntryFields(Integer numberOfPassengers) {
    if (numberOfPassengers == null) {
        System.out.println("numberOfPassengers is null! Defaulting to 1.");
        numberOfPassengers = 1;
    }

    passengerBox.getChildren().clear();

    for (int i = 1; i <= numberOfPassengers; i++) {
        VBox singlePassengerBox = new VBox(10);
        singlePassengerBox.setStyle("-fx-border-color: lightgray; -fx-padding: 15; -fx-border-radius: 5;");

        Label passengerLabel = new Label("Passenger " + i);

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last name");

        HBox nameRow = new HBox(10, firstNameField, lastNameField);

        VBox passengerDetails = new VBox(10);
        passengerDetails.getChildren().add(passengerLabel);

        // Only for the first passenger
        if (i == 1) {
            CheckBox useAccountInfo = new CheckBox("Use my Spoink Account Info");
            passengerDetails.getChildren().add(useAccountInfo);

            useAccountInfo.setOnAction(e -> {
                if (useAccountInfo.isSelected() && UserSession.isLoggedIn()) {
                    firstNameField.setText(UserSession.getFirstName());
                    lastNameField.setText(UserSession.getLastName());
                } else {
                    firstNameField.clear();
                    lastNameField.clear();
                }
            });
        }

        passengerDetails.getChildren().addAll(nameRow);

        singlePassengerBox.getChildren().add(passengerDetails);
        passengerBox.getChildren().add(singlePassengerBox);
    }
    }

    public void showOutboundFlightDetails(String departureCode, String arrivalCode, String departDate, String departTime, String arrivalTime, 
                                  String duration, double price) {
        
        flightInfoBox.getChildren().clear();  // Clear previous info if any

        VBox flightDetailsBox = new VBox(8);
        flightDetailsBox.setStyle("-fx-padding: 15; -fx-background-color: #f2f2f2; -fx-border-color: lightgray; -fx-border-radius: 5;");

        Label title = new Label("Outbound Flight");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        Label flightPlanLabel = new Label("Route: " + departureCode + " → " + arrivalCode);
        Label flightDateLabel = new Label("Date: " + departDate);
        Label departTimeLabel = new Label("Departure Time: " + departTime);
        Label arrivalTimeLabel = new Label("Arrival Time: " + arrivalTime);
        Label durationLabel = new Label("Duration: " + duration);
        Label priceLabel = new Label("Cost: $" + String.format("%.2f", price));

        flightDetailsBox.getChildren().addAll(
            title, flightPlanLabel, flightDateLabel,
            departTimeLabel, arrivalTimeLabel, durationLabel,
            priceLabel
        );

        flightInfoBox.getChildren().add(flightDetailsBox);
    }

    public void showInboundFlightDetails(String departureCode, String arrivalCode, String departDate, String departTime, String arrivalTime, 
                                  String duration, double price) {

        VBox returnFlightBox = new VBox(8);
        returnFlightBox.setStyle("-fx-padding: 15; -fx-background-color: #f2f2f2; -fx-border-color: lightgray; -fx-border-radius: 5;");

        Label title = new Label("Return Flight");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        Label flightPlanLabel = new Label("Route: " + departureCode + " → " + arrivalCode);
        Label flightDateLabel = new Label("Date: " + departDate);
        Label departTimeLabel = new Label("Departure Time: " + departTime);
        Label arrivalTimeLabel = new Label("Arrival Time: " + arrivalTime);
        Label durationLabel = new Label("Duration: " + duration);
        Label priceLabel = new Label("Cost: $" + String.format("%.2f", price));

        returnFlightBox.getChildren().addAll(
            title, flightPlanLabel, flightDateLabel,
            departTimeLabel, arrivalTimeLabel, durationLabel,
            priceLabel
        );

        flightInfoBox.getChildren().add(returnFlightBox);
    }
    
}