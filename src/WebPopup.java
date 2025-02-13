import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebPopup extends Application {
	
	 @Override
	    public void start(Stage primaryStage) {
	        // Create the WebView and WebEngine
	        WebView webView = new WebView();
	        WebEngine webEngine = webView.getEngine();

	        // Load the desired website
	        webEngine.load("https://thepointsguy.com/deals/");

	        // Create a new stage (popup window)
	        Stage popupStage = new Stage();
	        popupStage.setTitle("Travel Deals");
	        popupStage.setScene(new Scene(webView, 800, 600));  // Set size of popup
	        popupStage.show();  // Display the popup
	    }

	    public static void main(String[] args) {
	        launch(args);  // Launch the JavaFX application
	    }

}
