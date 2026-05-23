import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MonitorBugs extends JFrame {

    JTable table;
    DefaultTableModel model;

    public MonitorBugs() {
        setTitle("Monitor Bugs");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Bug ID");
        model.addColumn("Title");
        model.addColumn("Type");
        model.addColumn("Severity");
        model.addColumn("Priority");
        model.addColumn("Project");
        model.addColumn("Assigned To");
        model.addColumn("Status");

        // Load the data from DB
        loadData();

        // Add scrollable table to center
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons Panel
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back");

        // Refresh button action
        refreshButton.addActionListener(e -> {
            model.setRowCount(0); // clear old data
            loadData(); // reload fresh data
        });

        // Back button action
        backButton.addActionListener(e -> {
            dispose(); // close current window
            SwingUtilities.invokeLater(() -> {
                ProjectManagerWindow pmw = new ProjectManagerWindow();
                pmw.setVisible(true);
                pmw.setLocationRelativeTo(null);
            });
        });

        // Add buttons to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadData() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT bug_id AS id, bug_name AS title, bug_type AS type, bug_level AS severity, " +
                         "bug_priority AS priority, project_name, assigned_to, status FROM bugs";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("type"),
                    rs.getString("severity"),
                    rs.getString("priority"),
                    rs.getString("project_name"),
                    rs.getString("assigned_to"),
                    rs.getString("status")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error loading bugs: " + e.getMessage());
        }
    }

    // For standalone testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MonitorBugs::new);
    }
}
