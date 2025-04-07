
package FlightSearch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Search.fxml"));

        primaryStage.setTitle("Flight-Search");
        primaryStage.setScene(new Scene(root, 782, 473));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
