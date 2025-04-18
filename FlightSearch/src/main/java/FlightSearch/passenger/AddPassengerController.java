package passenger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddPassengerController implements Initializable {

    @FXML
    private Button proceedToSummary;

    @FXML
    private ComboBox<String> titleCombobox;
    @FXML
    private ComboBox<String> mealPrefBox;

    @FXML
    private ComboBox<String> seatPrefBox;


    @FXML
    private TextField firstNameField, lastNameField, emailField, phoneField, cityField, stateField;
    


    @FXML
    private DatePicker dobPicker;

    @Override
public void initialize(URL location, ResourceBundle resources) {
    try {
        // Load title options from database
        Connection conn = FlightSearch.DatabaseTitle.getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT name FROM titles");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            titleCombobox.getItems().add(rs.getString("name"));
        }
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Database Error");
        alert.setHeaderText("Failed to Load Titles");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    mealPrefBox.getItems().addAll("Vegetarian", "Non-Vegetarian", "Vegan", "Kosher");
    seatPrefBox.getItems().addAll("Window", "Middle", "Aisle");
}


@FXML
private void handleProceed(javafx.event.ActionEvent event) throws IOException {
    {
    System.out.println("Proceed button clicked!");
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/passenger/Summarypage.fxml"));
    Parent root = loader.load();

    Stage stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}
}


}