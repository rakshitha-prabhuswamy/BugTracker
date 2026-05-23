import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MonitorDevelopers extends JFrame {

    JTable table;
    DefaultTableModel model;

    public MonitorDevelopers() {
        setTitle("Monitor Developers");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🧱 Table setup
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");

        // Load data initially
        loadData();

        // Add table to scroll pane in center
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔘 Create buttons
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back");

        // 🎯 Add button actions
        refreshButton.addActionListener(e -> {
            model.setRowCount(0); // Clear table before reloading
            loadData();
        });

        backButton.addActionListener(e -> {
            dispose(); // Close this window
            SwingUtilities.invokeLater(() -> {
                ProjectManagerWindow pmw = new ProjectManagerWindow();
                pmw.setVisible(true);
                pmw.setLocationRelativeTo(null);
            });
        });

        // 🔳 Add buttons to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadData() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, name, email FROM users WHERE role='Developer'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error loading developers: " + e.getMessage());
        }
    }

    // For standalone testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MonitorDevelopers::new);
    }
}
