package registration.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FilteredOutputTesting extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/oneWayResults.fxml"));
        Parent root = loader.load();

        OneWayFlightResultsController controller = loader.getController();

        //Succesful test input
        /* String departure = "(JFK) John F. Kennedy International Airport";
        String arrival = "(LAX) Los Angeles International Airport";
        controller.searchCriteria(departure, arrival); */

        //Unsuccessful test input
        String wrongDeparture = "(XXX) Somthing Airport";
        String wrongArrival = "(YYY) Everything Airport";
        controller.searchCriteria(wrongDeparture, wrongArrival);

        //String nullDeparture = "";
        //String nullArrival = "";
        //controller.searchCriteria(nullDeparture, nullArrival);

        

        Scene scene = new Scene(root);
        primaryStage.setTitle("JavaFX Filtered Output Testing");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Required to start the JavaFX application
    }
}