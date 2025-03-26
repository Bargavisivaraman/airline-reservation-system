import java.sql.*;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/pokemon?allowPublicKeyRetrieval=true&useSSL=false";
        String user = "root";
        String password = "Arrshan_12";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish Connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");

            // Create Statement
            Statement stmt = conn.createStatement();
            String query = "select * from POKEDEX";
            ResultSet rs = stmt.executeQuery(query);

            // Process ResultSet
            while (rs.next()) {
                System.out.println("entry: " + rs.getInt("entry") + ", Name: " + rs.getString("name") + ", Type: " + rs.getString("Type") + ", HP:" + rs.getString("HP"));
            }

            // Close resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

