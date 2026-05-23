import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CheckPerformance extends JFrame {

    private JTextArea area;

    public CheckPerformance() {
        setTitle("Developer Performance Summary");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Developer Performance Report", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(area), BorderLayout.CENTER);

        // 🔘 Create Buttons
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back");

        // 🎯 Add Action Listeners
        refreshButton.addActionListener(e -> loadData());

        backButton.addActionListener(e -> {
            dispose(); // close current CheckPerformance window
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

        loadData();
        setVisible(true);
    }

    private void loadData() {
        area.setText("Loading data, please wait...\n");
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT assigned_to, " +
                    "COUNT(*) AS total_bugs, " +
                    "SUM(CASE WHEN status='Resolved' THEN 1 ELSE 0 END) AS fixed_bugs " +
                    "FROM bugs " +
                    "WHERE assigned_to IS NOT NULL AND assigned_to <> '' " +
                    "GROUP BY assigned_to";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            boolean found = false;

            while (rs.next()) {
                found = true;
                sb.append("Developer: ").append(rs.getString("assigned_to")).append("\n");
                sb.append("Total Bugs: ").append(rs.getInt("total_bugs")).append("\n");
                sb.append("Fixed Bugs: ").append(rs.getInt("fixed_bugs")).append("\n");
                sb.append("-----------------------------\n");
            }

            if (!found) {
                sb.append("No developer performance data found.\n");
            }

            area.setText(sb.toString());
        } catch (SQLException e) {
            area.setText("❌ Error fetching data: " + e.getMessage());
        }
    }

    // For standalone testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CheckPerformance::new);
    }
}
