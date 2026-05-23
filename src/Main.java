import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Try connecting to the MySQL database
            Connection conn = DBConnection.getConnection();
            System.out.println("✅ Connected to the database successfully!");
            conn.close(); // Close after testing
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            return; // stop execution if connection fails
        }

        // If connection works, launch the main menu window
        java.awt.EventQueue.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}
