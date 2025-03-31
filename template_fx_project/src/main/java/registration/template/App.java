package registration.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Inside start::" +getClass());
        Parent root = FXMLLoader.load(getClass().getResource("/registration/template/FlightSearch.fxml"));
        System.out.println("after FXML Loader");
        stage.setTitle("Flight Search");
        stage.setScene(new Scene(root, 800, 500)); //setting screen dimensions 
        //FlightSearchController fSController = new FlightSearchController();
        System.out.println("After object creation");
        
        stage.show(); // show the screen
    } 

    public static void main(String[] args) {
        System.out.println("Before launch");
        launch(args);
        System.out.println("After launch");
    }
}