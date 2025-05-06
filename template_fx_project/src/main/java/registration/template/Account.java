package registration.template;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Account {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String storedFirstName;

    @FXML
    private Label firstNameValue;

    @FXML
    private Label lastNameValue;

    @FXML
    private Label emailValue;

    @FXML 
    private VBox bookingContainer;

    @FXML 
    private Button logoutButton;

    @FXML 
    private Button accountButton;

    @FXML
    private Button signInButton;

    public void setUserName(String firstName) {
        this.storedFirstName = firstName;

        firstNameValue.setText(UserSession.getFirstName());
        lastNameValue.setText(UserSession.getLastName());
        emailValue.setText(UserSession.getEmail());

        showBookings();
    }

    public void setUserData(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            storedFirstName = resultSet.getString("first_name");

            firstNameValue.setText(storedFirstName);
            lastNameValue.setText(resultSet.getString("last_name"));
            emailValue.setText(resultSet.getString("email"));

            showBookings();
        }
    }

    private void showBookings() {
        bookingContainer.getChildren().clear();
    
        List<Booking> bookings = UserSession.getBookings();
    
        if (bookings == null || bookings.isEmpty()) {
            bookingContainer.getChildren().add(new Label("No bookings found."));
        } else {
            for (Booking booking : bookings) {
                VBox bookingBox = new VBox(8);
                bookingBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");
                bookingBox.setPrefWidth(600);
    
                Label bookingIdLabel = new Label("Booking ID: " + booking.getBookingId());
                bookingIdLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
                bookingIdLabel.setWrapText(true);
    
                Label outboundLabel = new Label("Outbound: " + booking.getDepartureCode() + " → " + booking.getArrivalCode()
                        + " | " + booking.getDepartDate() + " @ " + booking.getDepartTime()
                        + " → " + booking.getArrivalTime()
                        + " | Duration: " + booking.getDuration());
                outboundLabel.setWrapText(true);
    
                Label passengerLabel = new Label("Passengers: " + booking.getPassengers().size());
                passengerLabel.setWrapText(true);
    
                Label priceLabel = new Label("Total Price: $" + String.format("%.2f", booking.getPrice()));
                priceLabel.setWrapText(true);
    
                // Add outbound first
                bookingBox.getChildren().addAll(bookingIdLabel, outboundLabel);
    
                // Then add return flight, if applicable
                if (booking.getReturnDepartureCode() != null) {
                    Label returnLabel = new Label("Return:   " + booking.getReturnDepartureCode() + " → " + booking.getReturnArrivalCode()
                            + " | " + booking.getReturnDepartDate() + " @ " + booking.getReturnDepartTime()
                            + " → " + booking.getReturnArrivalTime()
                            + " | Duration: " + booking.getReturnDuration());
                    returnLabel.setWrapText(true);
                    bookingBox.getChildren().add(returnLabel);
                }
    
                // Add passengers + price last
                bookingBox.getChildren().addAll(passengerLabel, priceLabel);
    
                bookingContainer.getChildren().add(bookingBox);
            }
        }
    }
    

    public void accountInfoToFlightSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FlightSearch.fxml"));
        Parent root = loader.load();

        FlightSearchController controller = loader.getController();
        if (storedFirstName != null) {
            controller.displayUserName(storedFirstName);
        }

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
