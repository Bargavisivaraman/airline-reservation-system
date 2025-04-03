import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getDBConnection() {
        String databaseName = "HW_Airports";
        String databaseUser = "root";
        String databasePassword = "Cookieloverr00#";
        String url = "jdbc:mysql://localhost:3306/" + databaseName + "?useSSL=false&serverTimezone=UTC";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);

        } catch (Exception e) {
            System.out.println("❌ Database connection failed:");
            e.printStackTrace();
        }

        return databaseLink;
    }

}
