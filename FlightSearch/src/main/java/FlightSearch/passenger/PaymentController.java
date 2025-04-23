package passenger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class PaymentController {

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expiryDateField;

    @FXML
    private PasswordField cvvField;

    @FXML
    private TextField cardHolderField;

    @FXML
    private void handleSubmitPayment(ActionEvent event) {
        String cardNumber = cardNumberField.getText();
        String expiry = expiryDateField.getText();
        String cvv = cvvField.getText();
        String cardHolder = cardHolderField.getText();

        if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty() || cardHolder.isEmpty()) {
            System.out.println("Please fill out all fields before submitting.");
        } else {
            System.out.println("Payment submitted successfully!");
            // Optional: Add navigation to confirmation page
        }
    }

    @FXML
    private void handleBackToSummary(ActionEvent event) {
        try {
            Parent summaryPage = FXMLLoader.load(getClass().getResource("/passenger/SummaryPage.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(summaryPage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
