package passenger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.util.Random;

public class ThankYouController {

    @FXML
    private Label thankYouLabel;

    @FXML
    private Label bookingRefLabel;

    @FXML
    public void initialize() {
        String bookingRef = generateBookingReference();
        bookingRefLabel.setText("Booking Reference: " + bookingRef);
    }

    private String generateBookingReference() {
        Random rand = new Random();
        int number = 100000 + rand.nextInt(900000); // 6-digit random number
        return "SPO" + number;
    }

    @FXML
    private void handleBackToHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/passenger/Passengerdetails.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
