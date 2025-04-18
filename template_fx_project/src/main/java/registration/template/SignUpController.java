package registration.template;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label signUpDisplay;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField pwdField;

    @FXML
    private TextField reservationIdField;

    public void displayText() {
        
    }
    
    public void signUpToSignIn(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("/registration/template/signin.fxml"));
        root = Loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void insertCustomerData(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = pwdField.getText();
        //String reservationId = reservationIdField.getText();

        DatabaseConnection userConnection = new DatabaseConnection();
        Connection conn2 = userConnection.getDBConnection();
        PreparedStatement stmt;

        String userQuery = "INSERT INTO users (first_name, last_name, email, pwd) VALUES (?, ?, ?, ?)";

        try {
            stmt = conn2.prepareStatement(userQuery);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, password);
        
            stmt.executeUpdate();
            System.out.println("User added to database!");

            // Optional: Clear fields after insert
            firstNameField.clear();
            lastNameField.clear();
            emailField.clear();
            pwdField.clear();
            // reservationIdField.clear();

            stmt.close();
            conn2.close();

        } catch (Exception SQLException) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, SQLException);
        }
    }

}