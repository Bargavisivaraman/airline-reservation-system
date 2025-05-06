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
                Label label = new Label("Booking ID: " + booking.getBookingId()
                    + " | " + booking.getSummary()
                    + " | Passengers: " + booking.getPassengers().size());
                label.setStyle("-fx-font-size: 14px;");
                bookingContainer.getChildren().add(label);
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
        stage.show();
    }
}
