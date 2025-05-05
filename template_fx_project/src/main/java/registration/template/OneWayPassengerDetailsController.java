package registration.template;

import java.io.IOException;
import java.net.URL;
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



public class OneWayPassengerDetailsController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox passengerBox;

    @FXML
    private Label flightPlanText;
    @FXML
    private Label flightTotalCost;

    @FXML
    private void backToFlightResults(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/OneWayResults.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.setTitle("Flight Results");
        stage.show(); // show the screen
        
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        populateEntryFields(numberOfPassengers);
    }

    public void populateEntryFields(Integer numberOfPassengers) {
        if (numberOfPassengers == null) {
            System.out.println("⚠ numberOfPassengers is null! Defaulting to 1.");
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

            TextField passportField = new TextField();
            passportField.setPromptText("Passport Number");



            HBox nameRow = new HBox(10, firstNameField, lastNameField);

            VBox passengerDetails = new VBox(10);
            passengerDetails.getChildren().add(passengerLabel);

            
            if (i == 1) {
                CheckBox useAccountInfo = new CheckBox("Use my Spoink Account Info");
                passengerDetails.getChildren().add(useAccountInfo);
            }

            passengerDetails.getChildren().addAll(nameRow, passportField);

            singlePassengerBox.getChildren().add(passengerDetails);
            passengerBox.getChildren().add(singlePassengerBox);
        }
    }

    public void showFlightDetails(String departureCode, String arrivalCode, String departDate, String departTime, String arrivalTime, 
                                  String duration, double price) {

        flightPlanText.setText(departureCode + " → " + arrivalCode);
        flightTotalCost.setText("Total cost: $" + price);

    }
    
}
