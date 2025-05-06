package registration.template;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class OneWayFlightReviewController implements Initializable {

    private Scene scene;
    private Parent root;

    // FXML Fields (Matching your new FXML)
    @FXML
    private Label flightPlan;

    @FXML
    private Label outboundDetails;

    @FXML
    private Label outBoundDate;

    @FXML
    private Label departureTime;

    @FXML
    private Label flightRoute;

    @FXML
    private Label aircraftDetails;

    @FXML
    private Label classNPrice;

    @FXML
    private Label totalCost;

    @FXML
    private Button continueToPassengerButton;

    @FXML
    private Button backToResultsButton;

    // --- Data Fields ---
    private String departureLocation;
    private String arrivalLocation;
    private String flightDate;
    private String depTime;
    private String arrTime;
    private String duration;
    private double totalPrice;

    private Integer passengerCount;

    public void storePassengerCountInfo(Integer count) {
        this.passengerCount = count;
    }

    public void storeFlightDate(String returnFlightDate) {
        this.flightDate = returnFlightDate;
    }

    // --- Method to set all flight review data ---
    public void setFlightReviewDetails(String departureLocation, String arrivalLocation, String flightDate,
                                       String depTime, String arrTime,
                                       String duration, double totalPrice) {
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.flightDate = flightDate;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.duration = duration;
        this.totalPrice = totalPrice;

        // Calculate total price using passenger count
        if (passengerCount > 1) {
            this.totalPrice = totalPrice * passengerCount;
        } else {
            this.totalPrice = totalPrice;
        }

        updateReviewUI();
    }

    // --- Update the FXML labels ---
    private void updateReviewUI() {
        flightPlan.setText(departureLocation + " ➝ " + arrivalLocation);
        outboundDetails.setText("Outbound flight from " + departureLocation + " to " + arrivalLocation);
        outBoundDate.setText(flightDate);
        departureTime.setText(depTime + " → " + arrTime);
        flightRoute.setText(departureLocation + " ➝ " + arrivalLocation);
        totalCost.setText(String.format("USD %.2f", totalPrice));
    }

    public void backToFlightResults(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/OneWayFlightResults.fxml"));
        root = loader.load();

        OneWayFlightResultsController resultsPage = loader.getController();
        resultsPage.searchFromReview(departureLocation, arrivalLocation);
        resultsPage.storePassengerCountInfo(passengerCount);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();   
    }

    public void continueToPassengers(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/OneWayPassengerDetails.fxml"));
        root = loader.load();

        OneWayPassengerDetailsController passengerInfoEntryPage = loader.getController();
        passengerInfoEntryPage.showFlightDetails(departureLocation, arrivalLocation, flightDate, depTime, arrTime,
                duration, totalPrice);
        passengerInfoEntryPage.setNumberOfPassengers(passengerCount);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
