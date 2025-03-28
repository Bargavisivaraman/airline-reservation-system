import java.sql.*;

public class addpokemon {

    static final String url = "jdbc:mysql://localhost:3306/pokemon?allowPublicKeyRetrieval=true&useSSL=false";
    static final String user = "root";
    static final String password = "Cookieloverr00#";

    public static void main(String[] args) {
        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");

            // Example usage
            addPokemon(conn, 152, "boogabooga", "Grass", 45);
            editPokemon(conn, 25, "Pikachu", "Electric", 50); // Updates the entry with entry = 25

            // Close connection
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addPokemon(Connection conn, int entry, String name, String type, int hp) {
        String sql = "INSERT INTO pokedex (entry, name, type, HP) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entry);
            pstmt.setString(2, name);
            pstmt.setString(3, type);
            pstmt.setInt(4, hp);
            pstmt.executeUpdate();
            System.out.println("New Pokémon added: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editPokemon(Connection conn, int entry, String newName, String newType, int newHP) {
        String sql = "UPDATE pokedex SET name = ?, type = ?, HP = ? WHERE entry = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, newType);
            pstmt.setInt(3, newHP);
            pstmt.setInt(4, entry);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Pokémon entry " + entry + " updated.");
            } else {
                System.out.println("No Pokémon found with entry " + entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
