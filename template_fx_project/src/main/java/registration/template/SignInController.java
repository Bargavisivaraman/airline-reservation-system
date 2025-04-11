package registration.template;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class SignInController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label signInDisplay;
 
    public void displayText() {
         
    }

    /* public void switchToFlightSearch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FlightSearch.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } */
}