package passenger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Random;

public class BookingConfirmationController {

    @FXML
    private Label confirmationMessageLabel;

    @FXML
    private Label bookingRefLabel;

    public void setPassengerName(String name) {
        confirmationMessageLabel.setText("Booking Confirmed for " + name);
        bookingRefLabel.setText("Booking Reference: " + generateReference());
    }

    private String generateReference() {
        Random rand = new Random();
        int number = 100000 + rand.nextInt(900000); // generates a 6-digit number
        return "SPO" + number;
    }

    @FXML
    private void handleBackToHome(ActionEvent event) {
        try {
            Parent homePage = FXMLLoader.load(getClass().getResource("/passenger/Passengerdetails.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(homePage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
