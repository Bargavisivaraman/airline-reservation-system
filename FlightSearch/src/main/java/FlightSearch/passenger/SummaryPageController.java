package passenger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class SummaryPageController {

    @FXML
    private Label flightDetailsLabel;

    @FXML
    private Label passengerDetailsLabel;

    // This method is called from AddPassengerController
    public void setPassengerDetails(String details) {
        passengerDetailsLabel.setText(details);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        System.out.println("Going back to Passenger Details...");
        // You can add scene switching code here if needed
    }

    @FXML
    private void handleProceedToPayment(ActionEvent event) {
        System.out.println("Proceeding to Payment...");
        // You can add logic to go to payment page here
    }
}
