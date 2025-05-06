package registration.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;


public class FlightSearchController {

    // If your ComboBox is going to display Strings, you should define that datatype here
    @FXML
    private ComboBox<String> fromDropDown, toDropDown;

    @FXML
    private Button searchButton;


    @FXML
    private ComboBox<Integer> noOfPassengersDropDown;

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ToggleGroup choice;

    @FXML
    private RadioButton roundTripRadioButton, oneWayRadioButton;
    
    @FXML
    private DatePicker returnDate, departDate;

    @FXML
    private Label userGreeting;

    @FXML
    private Button logoutButton;

    @FXML
    private Button signInButton;

    @FXML
    private Button accountButton;

    private String storedFirstName;

    @FXML
    private void initialize() {

        ToggleGroup choice = new ToggleGroup();
        roundTripRadioButton.setToggleGroup(choice);
        oneWayRadioButton.setToggleGroup(choice);

        System.out.println("inside initialize");
        // Within this initialize method, you can initialize the data for the ComboBox. I have changed the
        // method from fillComboBox2() to getData(), which returns a List of Strings.
        // We need to set the ComboBox to use that list.
        fromDropDown.setEditable(true);
        toDropDown.setEditable(true);
        noOfPassengersDropDown.setEditable(false);
        ObservableList<String> airportNameList = FXCollections.observableArrayList(getData());
        ObservableList<Integer> noOfPassengers = FXCollections.observableArrayList();
        noOfPassengers.addAll(1, 2, 3, 4, 5);

        System.out.println(noOfPassengers);
            
        //fromDropDown.setItems(FXCollections.observableArrayList(getData()));
        //toDropDown.setItems(FXCollections.observableArrayList(getData()));
        fromDropDown.setItems(airportNameList);
        toDropDown.setItems(airportNameList);
        noOfPassengersDropDown.setItems(noOfPassengers);
        
        oneWayRadioButton.setOnMouseClicked(event ->  {
            returnDate.setVisible(false);
        });
        roundTripRadioButton.setOnMouseClicked(event ->  {
            returnDate.setVisible(true);
        });

        //creates an instance of FilteredList with parameters of airportNameList that have been previously populated
        FilteredList<String> filteredFromList = new FilteredList<>(airportNameList, s -> true);
        FilteredList<String> filteredToList = new FilteredList<>(airportNameList, s -> true);

        //populates fromDropdown and toDropdown with their corresponding filteredlists
        fromDropDown.setItems(filteredFromList);
        toDropDown.setItems(filteredToList);

        // from dropdown filtering
        fromDropDown.getEditor().textProperty().addListener((obs,oldValue,newValue)-> {
            final TextField editor =fromDropDown.getEditor();
            final String selected = fromDropDown.getSelectionModel().getSelectedItem();

            if(selected == null || !selected.equals(editor.getText())){
                filteredFromList.setPredicate(item -> {
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }

                    String lowercaseFilter = newValue.toLowerCase();
                    return item.toLowerCase().contains(lowercaseFilter);
                });
            }

            if (!UserSession.isLoggedIn()) {
                disableFlightSearchUI();
            }

        });

        //To dropDown filtering
        toDropDown.getEditor().textProperty().addListener((obs,oldValue,newValue)-> {
            final TextField editor = toDropDown.getEditor();
            final String selected = toDropDown.getSelectionModel().getSelectedItem();

            if(selected == null || !selected.equals(editor.getText())){
                filteredToList.setPredicate(item -> {
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowercaseFilter = newValue.toLowerCase();
                    return item.toLowerCase().contains(lowercaseFilter);
                });

            }
        });

        fromDropDown.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                toDropDown.setItems(FXCollections.observableArrayList(
                    airportNameList.filtered(item -> !item.equals(newVal))
                ));
            } else {
                toDropDown.setItems(airportNameList);
            }
        });

        toDropDown.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                fromDropDown.setItems(FXCollections.observableArrayList(
                    airportNameList.filtered(item -> !item.equals(newVal))
                ));
            } else {
                fromDropDown.setItems(airportNameList);
            }
        });

        departDate.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); 
                }
            }
        });
        
        returnDate.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }

                LocalDate depart = departDate.getValue();
                if (date.isBefore(depart.plusDays(1))) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

    }

    private void disableFlightSearchUI() {
        fromDropDown.setDisable(true);
        toDropDown.setDisable(true);
        noOfPassengersDropDown.setDisable(true);
        roundTripRadioButton.setDisable(true);
        oneWayRadioButton.setDisable(true);
        departDate.setDisable(true);
        returnDate.setDisable(true);
    }

    public void displayUserName(String firstName) {
        this.storedFirstName = firstName;
        userGreeting.setText("Hello, " + firstName + "!");
        userGreeting.setVisible(true);
        logoutButton.setVisible(true);
        accountButton.setVisible(true);
        signInButton.setVisible(false);
    }


    public void goToSignIn(ActionEvent event) throws IOException {
        System.out.println("Inside goToSignIn");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/SignInPage.fxml"));
        root = loader.load();
        System.out.println("After loading the root");

        //SignInController controller = loader.getController();
        //controller.displayText();
        System.out.println("after object creation of sign in controller and calling the displayText method");

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.setTitle("Sign In Page");
        stage.setMaximized(true);
        stage.show(); // show the screen
    }

    public void goToAccount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountPage.fxml"));
        root = loader.load();
    
        Account accountController = loader.getController();
        accountController.setUserName(storedFirstName); // Pass the user's name
    
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void searchFlights(ActionEvent event) throws IOException {
      
        System.out.println("Inside searchFlights");

        if (!UserSession.isLoggedIn()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Authentication Required");
            alert.setHeaderText(null);
            alert.setContentText("Please sign in before searching for flights.");
            alert.showAndWait();
            return;
        } 

        LocalDate departureDate = departDate.getValue();
        System.out.println(departureDate.toString());

        if(oneWayRadioButton.isSelected()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/OneWayFlightResults.fxml"));
            System.out.println("After fxml loader");
            root = loader.load();
            System.out.println("After loading the root");
    
            OneWayFlightResultsController flightResultsPage = loader.getController();
            flightResultsPage.searchCriteria(fromDropDown.getValue(), toDropDown.getValue());
            flightResultsPage.storePassengerCountInfo(noOfPassengersDropDown.getValue());
            flightResultsPage.setUserName(storedFirstName);
            flightResultsPage.storeFlightDate(departDate.getValue().toString());
    
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root); 
            stage.setScene(scene);
            stage.setTitle("One Way Flight Results");
            stage.setMaximized(true);
            stage.show(); // show the screen

        } else if (roundTripRadioButton.isSelected()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registration/template/OutboundFlightResults.fxml"));
            System.out.println("After fxml loader");
            root = loader.load();
            System.out.println("After loading the root");
    
            OutboundFlightResultsController2 outboundFlightPage = loader.getController();
            outboundFlightPage.searchCriteria(fromDropDown.getValue(), toDropDown.getValue());
            outboundFlightPage.storeDepartDate(departDate.getValue().toString());
            outboundFlightPage.storeReturnFlightDate(returnDate.getValue().toString());
            outboundFlightPage.storePassengerCountInfo(noOfPassengersDropDown.getValue());
            outboundFlightPage.setUserName(storedFirstName);
    
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root); 
            stage.setScene(scene);
            stage.setTitle("Outbound Flight Results");
            stage.setMaximized(true);
            stage.show(); // show the screen
        }
    }

    public void displayPreviousUserChoices(String departure, String arrival, LocalDate departDate) {
        fromDropDown.setValue(departure);
        toDropDown.setValue(arrival);
        //departDate.set(departDate);
    }

    private List<String> getData() {
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn1 = dbConn.getDBConnection();
        PreparedStatement statement;
    
        // Define the data you will be returning, in this case, a List of Strings for the ComboBox
        List<String> options = new ArrayList<>();

        System.out.println("line 39");
        try {
            
            String query = "select Airport_code, airportName FROM HW_Airport_List_T";
             statement = conn1.prepareStatement(query);

            ResultSet set = statement.executeQuery();

            while (set.next()) {
                String airportCode = set.getString("Airport_code");
                String airportName = set.getString("airportName");
                options.add("(" + airportCode + ")" + " " + airportName);
            }
            System.out.println("line 51");
            set.close();
            statement.close();
            conn1.close();
            // Return the List
            return options;

        } catch (SQLException e) {
            Logger.getLogger(FlightSearchController.class.getName()).log(Level.SEVERE, null, e);
        }
        return options;
    }
    
}