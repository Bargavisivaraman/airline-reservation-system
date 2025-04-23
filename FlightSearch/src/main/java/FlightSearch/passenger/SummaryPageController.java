package passenger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class SummaryPageController {

    @FXML
    private Label flightDetailsLabel;

    @FXML
    private Label passengerDetailsLabel;

    // This method gets all the passenger info and displays it nicely
    public void setPassengerData(String title, String firstName, String lastName, String email,
                                 String phone, String city, String state, String dob,
                                 String mealPref, String seatPref) {

        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(title).append(" ").append(firstName).append(" ").append(lastName).append("\n");
        info.append("Email: ").append(email).append("\n");
        info.append("Phone: ").append(phone).append("\n");
        info.append("City: ").append(city).append("\n");
        info.append("State: ").append(state).append("\n");
        info.append("Date of Birth: ").append(dob).append("\n");
        info.append("Meal Preference: ").append(mealPref).append("\n");
        info.append("Seat Preference: ").append(seatPref);

        passengerDetailsLabel.setText(info.toString());
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent previousPage = FXMLLoader.load(getClass().getResource("/passenger/Passengerdetails.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(previousPage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProceedToPayment(ActionEvent event) {
        try {
            Parent paymentPage = FXMLLoader.load(getClass().getResource("/passenger/PaymentPage.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(paymentPage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
