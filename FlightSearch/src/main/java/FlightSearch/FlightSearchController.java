package FlightSearch;

import java.sql.Statement;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;

public class FlightSearchController implements Initializable {

    @FXML
    private Button ConfirmFromButton;

    @FXML
    private Button ConfirmToButton;

    // variable of type TextField using the id name of the textfield created
    @FXML
    private TextField FromSField;

    @FXML

    private TextField ToSField;

    // variable of type TableView using the id name of the textfield created
    @FXML
    private TableColumn<FlightSearchModel, Integer> CountryCodeTableColumn;

    @FXML
    private TableColumn<FlightSearchModel, String> CountryNameTableColumn;

    @FXML
    private TableColumn<FlightSearchModel, String> CityNameTableColumn;

    @FXML
    private TableColumn<FlightSearchModel, String> AirportNameTableColumn;

    @FXML
    private TableView<FlightSearchModel> resultsTable;

    ObservableList<FlightSearchModel> FlightSearchModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resource) {

       Connection conn = DatabaseConnection.getConnection();

        String searchViewQuery = "SELECT id, iata_code, airport_name, city_name, country_name FROM HW_Airport_List_T";

        // Initially hide the table
        resultsTable.setVisible(false);

        // Show the table when the search bar is clicked
        // using .setMouseClicked event lisener
        FromSField.setOnMouseClicked(event -> {
            System.out.println("clicked");
            resultsTable.setVisible(true);
        });

        // database connection
        try {
            Statement statement = conn.createStatement();

            ResultSet queryOutput = statement.executeQuery(searchViewQuery);
            // loops thoruh database
            while (queryOutput.next()) {

                Integer queryID = queryOutput.getInt("id");
                String queryiata_code = queryOutput.getString("iata_code");
                String airport_name = queryOutput.getString("airport_name");
                String city_name = queryOutput.getString("city_name");
                String country_name = queryOutput.getString("country_name");

                // populate observable list
                FlightSearchModelObservableList
                        .add(new FlightSearchModel(queryID, queryiata_code, airport_name, city_name, country_name));

            }

            CountryCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("countryID"));
            CountryNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("country_name"));
            CityNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("city_name"));
            AirportNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("airport_name"));

            resultsTable.setItems(FlightSearchModelObservableList);
            // initialize filtered list
            FilteredList<FlightSearchModel> filterData = new FilteredList<>(FlightSearchModelObservableList, b -> true);

            // action listener on filtering data from "where" searchbar
            FromSField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate(flightSearchModel -> {
                    if (newValue == null || newValue.isBlank()) {
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (flightSearchModel.getCity_name().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else if (flightSearchModel.getAirport_name().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else if (flightSearchModel.getCountryID().toString().contains(searchKeyword)) {
                        return true;

                    } else if (flightSearchModel.getIata_code().toLowerCase().contains(searchKeyword)) {
                        return true;

                    }

                    return false; // if nothing matches
                });
            });

            // action listerner for filterdata on "to" search bar
            ToSField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate(flightSearchModel -> {
                    if (newValue == null || newValue.isBlank()) {
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (flightSearchModel.getCity_name().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else if (flightSearchModel.getAirport_name().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else if (flightSearchModel.getCountryID().toString().contains(searchKeyword)) {
                        return true;

                    } else if (flightSearchModel.getIata_code().toLowerCase().contains(searchKeyword)) {
                        return true;

                    }

                    return false; // if nothing matches
                });
            });

            // Mutable boolean array. must be an array as regular variebles must be final
            // inside initialized.
            final boolean[] isSelectingFrom = { true };
            // sets table to visible and isSelectingFrom true on the FromSfield Searchbar
            FromSField.setOnMouseClicked(event -> {
                isSelectingFrom[0] = true;
                resultsTable.setVisible(true);
            });
            // sets table to visible and isSelectingFrom false on the ToSfield Searchbar
            ToSField.setOnMouseClicked(event -> {
                isSelectingFrom[0] = false;
                resultsTable.setVisible(true);
            });
            // listener to popullate the searchbars depending on the boolean variable
            // isSelectingFrom
            resultsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    String selectedText = newVal.getCountry_name() + ", " +
                            newVal.getCity_name() + " " +
                            newVal.getAirport_name();

                    if (isSelectingFrom[0]) {
                        FromSField.setText(selectedText);
                    } else {
                        ToSField.setText(selectedText);
                    }
                }
            });

            SortedList<FlightSearchModel> sortedData = new SortedList<>(filterData);

            sortedData.comparatorProperty().bind(resultsTable.comparatorProperty());

            resultsTable.setItems(sortedData);

            // TableColumn.setOnMouseClicked(event ->){
            // FromSField.setTo(TableColumn);
            // }

        } catch (SQLException e) {
            Logger.getLogger(FlightSearchController.class.getName()).log(null, null, e);
        }

    }

}
