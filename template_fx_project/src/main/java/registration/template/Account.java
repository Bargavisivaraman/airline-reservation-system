package registration.template;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Account {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String storedFirstName; // Store name for later use

    @FXML
    private Label firstNameValue;

    @FXML
    private Label lastNameValue;

    @FXML
    private Label emailValue;

    @FXML
    private Label reservationValue;

    @FXML 
    private Label userGreeting;

    @FXML 
    private Button logoutButton;

    @FXML 
    private Button accountButton;

    @FXML
    private Button signInButton;

    public void setUserName(String firstName) {
        this.storedFirstName = firstName;
    
        // Load all info from UserSession
        firstNameValue.setText(UserSession.getFirstName());
        lastNameValue.setText(UserSession.getLastName());
        emailValue.setText(UserSession.getEmail());
    
        String reservation = UserSession.getReservationId();
        if (reservation == null || reservation.isEmpty()) {
            reservationValue.setText("No Reservation");
        } else {
            reservationValue.setText(reservation);
        }
    }

    // Called from SignInController to fill in account data
    public void setUserData(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            storedFirstName = resultSet.getString("first_name"); // Save for back button

            firstNameValue.setText(storedFirstName);
            lastNameValue.setText(resultSet.getString("last_name"));
            emailValue.setText(resultSet.getString("email"));

            String reservationId = resultSet.getString("reservation_id");
            reservationValue.setText((reservationId == null || reservationId.isEmpty()) ? "No Reservation" : reservationId);
        }
    }

    public void accountInfoToFlightSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FlightSearch.fxml"));
        Parent root = loader.load();
    
        FlightSearchController controller = loader.getController();
    
        if (storedFirstName != null) {
            controller.displayUserName(storedFirstName); // sets the greeting
        } else {
            System.out.println("Warning: storedFirstName is null — user info wasn't passed.");
        }
    
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}