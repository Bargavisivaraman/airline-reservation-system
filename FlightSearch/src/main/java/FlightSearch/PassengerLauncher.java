package FlightSearch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;


public class PassengerLauncher extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = getClass().getClassLoader().getResource("passenger/Passengerdetails.fxml");
System.out.println("FXML path: " + fxmlLocation);
if (fxmlLocation == null) {
    throw new RuntimeException("FXML file not found!");
}
FXMLLoader loader = new FXMLLoader(fxmlLocation);
Parent root = loader.load();



        primaryStage.setTitle("Passenger Details");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
