package registration.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       System.out.println("Inside start::"+getClass());
        Parent root = FXMLLoader.load(getClass().getResource("/registration/template/registration.fxml"));
        stage.setTitle("User Registration");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Before launch");
        launch(args);
        System.out.println("After launch");
    }
}