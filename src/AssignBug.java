import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AssignBug extends JFrame {
    private JComboBox<String> bugComboBox, devComboBox, statusComboBox;
    private JButton assignButton, cancelButton, backButton;
    private TesterWindow parentWindow; // ✅ reference to TesterWindow

    // ✅ Constructor that takes parent window
    public AssignBug(TesterWindow parentWindow) {
        this.parentWindow = parentWindow;
        initUI();
    }

    // ✅ Default constructor (for standalone testing)
    public AssignBug() {
        initUI();
    }

    private void initUI() {
        setTitle("Assign Bug");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titleLabel = new JLabel("Assign Bug");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBounds(180, 20, 200, 30);
        add(titleLabel);

        JLabel bugLabel = new JLabel("Select Bug:");
        bugLabel.setBounds(80, 80, 100, 25);
        add(bugLabel);

        bugComboBox = new JComboBox<>();
        bugComboBox.setBounds(200, 80, 200, 25);
        add(bugComboBox);

        JLabel devLabel = new JLabel("Assign To:");
        devLabel.setBounds(80, 130, 100, 25);
        add(devLabel);

        devComboBox = new JComboBox<>();
        devComboBox.setBounds(200, 130, 200, 25);
        add(devComboBox);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(80, 180, 100, 25);
        add(statusLabel);

        statusComboBox = new JComboBox<>(new String[]{"Select", "Open", "In Progress", "Resolved", "Closed"});
        statusComboBox.setBounds(200, 180, 200, 25);
        add(statusComboBox);

        assignButton = new JButton("Assign Bug");
        assignButton.setBounds(80, 250, 120, 35);
        add(assignButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(210, 250, 120, 35);
        add(cancelButton);

        backButton = new JButton("Back");
        backButton.setBounds(340, 250, 100, 35);
        add(backButton);

        // Load data
        loadBugs();
        loadDevelopers();

        // Button actions
        assignButton.addActionListener(e -> assignBugToDeveloper());
        cancelButton.addActionListener(e -> dispose());
        backButton.addActionListener(e -> goBack());

        setVisible(true);
    }

    private void loadBugs() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT bug_id, bug_name FROM bugs")) {

            while (rs.next()) {
                bugComboBox.addItem(rs.getInt("bug_id") + " - " + rs.getString("bug_name"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading bugs: " + ex.getMessage());
        }
    }

    private void loadDevelopers() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM users WHERE role='Developer'")) {

            while (rs.next()) {
                devComboBox.addItem(rs.getString("name"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading developers: " + ex.getMessage());
        }
    }

   private void assignBugToDeveloper() {
    String selectedBug = (String) bugComboBox.getSelectedItem();
    if (selectedBug == null) {
        JOptionPane.showMessageDialog(this, "Please select a bug.");
        return;
    }

    int bugId = Integer.parseInt(selectedBug.split(" - ")[0]);
    String developer = (String) devComboBox.getSelectedItem();
    String status = (String) statusComboBox.getSelectedItem();

    if (developer == null || status == null || status.equals("Select")) {
        JOptionPane.showMessageDialog(this, "Please select developer and valid status.");
        return;
    }

    String sql = "UPDATE bugs SET assigned_to = ?, status = ? WHERE bug_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, developer);
        pstmt.setString(2, status);
        pstmt.setInt(3, bugId);

        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            JOptionPane.showMessageDialog(this, "✅ Bug assigned successfully!");

            // ✅ Clear selections after success
            bugComboBox.setSelectedIndex(-1);
            devComboBox.setSelectedIndex(-1);
            statusComboBox.setSelectedIndex(0);

            // ✅ Optionally reload bugs to reflect changes
            bugComboBox.removeAllItems();
            loadBugs();
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Failed to assign bug.");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
    }
}


    // ✅ Back button logic
    private void goBack() {
        if (parentWindow != null) {
            parentWindow.setVisible(true); // show the tester window again
        }
        dispose(); // close AssignBug
    }

    // ✅ Database connection class
    static class DBConnection {
        public static Connection getConnection() throws SQLException {
            String url = "jdbc:mysql://localhost:3306/bugtrackingsystem";
            String user = "root";  // change if needed
            String pass = "root";  // change if needed
            return DriverManager.getConnection(url, user, pass);
        }
    }

    // ✅ For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AssignBug::new);
    }
}
