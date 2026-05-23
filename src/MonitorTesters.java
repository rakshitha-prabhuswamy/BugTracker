import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MonitorTesters extends JFrame {

    JTable table;
    DefaultTableModel model;

    public MonitorTesters() {
        setTitle("Monitor Testers");
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

        // Load initial data
        loadData();

        // Add table in center inside a scroll pane
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔘 Buttons
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back");

        // 🎯 Button actions
        refreshButton.addActionListener(e -> {
            model.setRowCount(0); // Clear existing data
            loadData(); // Reload fresh data
        });

        backButton.addActionListener(e -> {
            dispose(); // Close this window
            SwingUtilities.invokeLater(() -> {
                ProjectManagerWindow pmw = new ProjectManagerWindow();
                pmw.setVisible(true);
                pmw.setLocationRelativeTo(null);
            });
        });

        // 🔳 Button panel at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadData() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, name, email FROM users WHERE role='Tester'";
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
            JOptionPane.showMessageDialog(this, "❌ Error loading testers: " + e.getMessage());
        }
    }

    // For standalone testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MonitorTesters::new);
    }
}
