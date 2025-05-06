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

public class RoundTripFlightReviewController2 implements Initializable {

    private Scene scene;
    private Parent root;

    @FXML
    private Label flightPlanLabel;

    @FXML
    private Label outboundDetailsLabel, outboundDateLabel, outFlightRouteLabel, outFlightTimesLabel, outPriceLabel;

    @FXML
    private Label inboundDetailsLabel, inboundDateLabel, inFlightRouteLabel, inFlightTimesLabel, inPriceLabel;
    
    @FXML
    private Label totalCost;

    @FXML
    private Button continueToPassengerButton;

    @FXML
    private Button backToResultsButton;

    //Outbound Details
    private String outDepLocation;
    private String outArrLocation;
    private String outFlightDate;
    private String outDepTime;
    private String outArrTime;
    private String outDuration;
    private double outPrice;

    //Inbound Details
    private String inDepLocation;
    private String inArrLocation;
    private String inFlightDate;
    private String inDepTime;
    private String inArrTime;
    private String inDuration;
    private double inPrice;

    private Integer passengerCount;

    public void storePassengerCountInfo(Integer count) {
        this.passengerCount = count;
        System.out.println("Passenger Count in round trip flight review: " + passengerCount);
    }

    public void storeReturnFlightDate(String returnFlightDate) {
        this.inFlightDate = returnFlightDate;
        System.out.println("Return Flight Date in round trip flight review: " + inFlightDate);
    }

    // --- Method to set all flight review data ---
    public void setOutboundFlightDetails(String departureLocation, String arrivalLocation, String flightDate,
                                         String depTime, String arrTime,
                                         String duration, double totalPrice) {
        this.outDepLocation = departureLocation;
        this.outArrLocation = arrivalLocation;
        this.outFlightDate= flightDate;
        this.outDepTime = depTime;
        this.outArrTime = arrTime;
        this.outDuration = duration;
        this.outPrice = totalPrice;

        updateOutboundReviewUI();
    }

    public void setInboundFlightDetails(String departureLocation, String arrivalLocation, String flightDate,
                                       String depTime, String arrTime,
                                       String duration, double totalPrice) {
        this.inDepLocation = departureLocation;
        this.inArrLocation = arrivalLocation;
        this.inFlightDate = flightDate;
        System.out.println(inFlightDate);
        this.inDepTime = depTime;
        this.inArrTime = arrTime;
        this.inDuration = duration;
        this.inPrice = totalPrice;

        updateInboundReviewUI();
    }

    public void backToFlightResults(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/OutboundFlightResults.fxml"));
        root = loader.load();

        OutboundFlightResultsController2 resultsPage = loader.getController();
        resultsPage.searchCriteria(outDepLocation, outArrLocation);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void continueToPassengers(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/RoundTripPassengerDetails.fxml"));
        root = loader.load();

        RoundTripPassengerDetailsController passengerInfoEntryPage = loader.getController();
        passengerInfoEntryPage.setNumberOfPassengers(passengerCount);
        passengerInfoEntryPage.showOutboundFlightDetails(outDepLocation, outArrLocation, outFlightDate, outDepTime, outArrTime,
                                                         outDuration, outPrice);
        passengerInfoEntryPage.showInboundFlightDetails(inDepLocation, inArrLocation, inFlightDate, inDepTime, inArrTime,
                                                        inDuration, inPrice);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    // --- Update the FXML labels ---
    private void updateOutboundReviewUI() {
        flightPlanLabel.setText(outDepLocation + " ➝ " + outArrLocation);
        outboundDetailsLabel.setText("Outbound flight from " + outDepLocation + " to " + outArrLocation);
        outboundDateLabel.setText("Your flight is on: " + outFlightDate);
        outFlightRouteLabel.setText(outDepLocation + " ➝ " + outArrLocation);
        outFlightTimesLabel.setText(outDepTime + " → " + outArrTime);
        outPriceLabel.setText(String.format("USD %.2f", outPrice));
    }

    private void updateInboundReviewUI() {
        inboundDetailsLabel.setText("Inbound flight from " + inDepLocation + " to " + inArrLocation);
        inboundDateLabel.setText("Your flight is on: " + inFlightDate);
        inFlightRouteLabel.setText(inDepLocation + " ➝ " + inArrLocation);
        inFlightTimesLabel.setText(inDepTime + " → " + inArrTime);
        inPriceLabel.setText(String.format("USD %.2f", inPrice));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}