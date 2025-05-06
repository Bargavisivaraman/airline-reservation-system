package registration.template;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class SignInController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private javafx.scene.control.TextField emailSignIn;

    @FXML
    private javafx.scene.control.PasswordField pwdSignIn;

    @FXML
    private Label signInDisplay;

    @FXML
    private Button signInButton;
 
    public void displayText() {
         
    }

    public void signInToFlightSearch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FlightSearch.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void signInToSignUp(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SignUpPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void confirmSignIn(ActionEvent event) throws IOException {
        String email = emailSignIn.getText();
        String password = pwdSignIn.getText();
    
        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in both email and password fields.");
            alert.showAndWait();
            return;
        }
    
        DatabaseConnection connectNow = new DatabaseConnection();
    
        try (Connection conn = connectNow.getDBConnection()) {
            String query = "SELECT * FROM users WHERE email = ? AND pwd = ?";
            java.sql.PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
    
            java.sql.ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                // Fetch user info from database
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String emailDB = rs.getString("email");
                String reservationId = rs.getString("reservation_id");

                // Save into UserSession
                UserSession.setFirstName(firstName);
                UserSession.setLastName(lastName);
                UserSession.setEmail(emailDB);
                UserSession.setReservationId(reservationId);
    
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Login Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Welcome, " + firstName + "!");
                successAlert.showAndWait();
    
                // Go to account.fxml and pass user data
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountPage.fxml"));
                root = loader.load();
    
                Account accountController = loader.getController();
                accountController.setUserData(rs);  // passes the ResultSet
    
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email or password.");
                alert.showAndWait();
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            signInDisplay.setText("Database error. Please try again later.");
        }
    }

    private void loadUserBookingsFromDatabase(String userEmail) {

        try (Connection conn = new DatabaseConnection().getDBConnection()) {
            String bookingQuery = "SELECT * FROM bookings WHERE user_email = ?";
            java.sql.PreparedStatement bookingStmt = conn.prepareStatement(bookingQuery);
            bookingStmt.setString(1, userEmail);

            java.sql.ResultSet bookingSet = bookingStmt.executeQuery();

            while (bookingSet.next()) {
                String bookingId = bookingSet.getString("booking_id");
                String departure = bookingSet.getString("departure_code");
                String arrival = bookingSet.getString("arrival_code");
                String date = bookingSet.getString("depart_date");
                String depTime = bookingSet.getString("depart_time");
                String arrTime = bookingSet.getString("arrival_time");
                String duration = bookingSet.getString("duration");
                double price = bookingSet.getDouble("price");

                // Now load passengers
                String passengerQuery = "SELECT * FROM passengers WHERE booking_id = ?";
                java.sql.PreparedStatement passengerStmt = conn.prepareStatement(passengerQuery);
                passengerStmt.setString(1, bookingId);
                java.sql.ResultSet passengerSet = passengerStmt.executeQuery();

                List<Passenger> passengers = new java.util.ArrayList<>();
                while (passengerSet.next()) {
                    String firstName = passengerSet.getString("first_name");
                    String lastName = passengerSet.getString("last_name");
                    passengers.add(new Passenger(firstName, lastName));
                }

                Booking booking = new Booking(
                    bookingId, departure, arrival, date,
                    depTime, arrTime, duration, price, passengers
                );

                UserSession.addBooking(booking);

                passengerSet.close();
                passengerStmt.close();
            }

            bookingSet.close();
            bookingStmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load bookings from Database");
        }
    }

}