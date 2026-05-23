import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddBug extends JFrame {

    // === Components ===
    private JTextField txtBugName, txtBugType, txtProjectName, txtStartDate, txtReportedBy, txtAssignedTo;
    private JComboBox<String> cmbPriority, cmbLevel, cmbSeverity, cmbStatus;
    private JButton btnAddBug, btnClear, btnBack;

    // Parent window reference
    private TesterWindow parentWindow;

    // Constructor with parent
    public AddBug(TesterWindow parentWindow) {
        this.parentWindow = parentWindow;
        initUI();
    }

    // Default constructor (for testing)
    public AddBug() {
        this.parentWindow = null;
        initUI();
    }

    private void initUI() {
        setTitle("🐞 Add New Bug");
        setSize(520, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // === Header ===
        JLabel lblTitle = new JLabel("Add New Bug Report", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(40, 80, 160));
        add(lblTitle, BorderLayout.NORTH);

        // === Form ===
        JPanel formPanel = new JPanel(new GridLayout(11, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(new JLabel("Bug Name:"));
        txtBugName = new JTextField();
        formPanel.add(txtBugName);

        formPanel.add(new JLabel("Bug Type:"));
        txtBugType = new JTextField();
        formPanel.add(txtBugType);

        formPanel.add(new JLabel("Project Name:"));
        txtProjectName = new JTextField();
        formPanel.add(txtProjectName);

        formPanel.add(new JLabel("Priority:"));
        cmbPriority = new JComboBox<>(new String[]{"Select", "Low", "Medium", "High"});
        formPanel.add(cmbPriority);

        formPanel.add(new JLabel("Bug Level:"));
        cmbLevel = new JComboBox<>(new String[]{"Select", "Minor", "Major", "Critical"});
        formPanel.add(cmbLevel);

        formPanel.add(new JLabel("Start Date (yyyy-MM-dd):"));
        txtStartDate = new JTextField();
        formPanel.add(txtStartDate);

        formPanel.add(new JLabel("Reported By:"));
        txtReportedBy = new JTextField();
        formPanel.add(txtReportedBy);

        formPanel.add(new JLabel("Assigned To:"));
        txtAssignedTo = new JTextField();
        formPanel.add(txtAssignedTo);

        formPanel.add(new JLabel("Severity:"));
        cmbSeverity = new JComboBox<>(new String[]{"Select", "Low", "Medium", "High", "Critical"});
        formPanel.add(cmbSeverity);

        formPanel.add(new JLabel("Status:"));
        cmbStatus = new JComboBox<>(new String[]{"Select", "Open", "In Progress", "Resolved", "Closed"});
        formPanel.add(cmbStatus);

        add(formPanel, BorderLayout.CENTER);

        // === Buttons ===
        JPanel btnPanel = new JPanel();
        btnAddBug = new JButton("Add Bug");
        btnClear = new JButton("Clear");
        btnBack = new JButton("Back");

        // Style buttons
        btnAddBug.setBackground(new Color(60, 120, 200));
        btnAddBug.setForeground(Color.WHITE);
        btnAddBug.setFocusPainted(false);
        btnAddBug.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnClear.setBackground(new Color(200, 70, 70));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnBack.setBackground(new Color(100, 100, 100));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnPanel.add(btnBack);
        btnPanel.add(btnAddBug);
        btnPanel.add(btnClear);
        add(btnPanel, BorderLayout.SOUTH);

        // === Button Actions ===
        btnAddBug.addActionListener(e -> addBugToDatabase());
        btnClear.addActionListener(e -> clearFields());

        // 🔙 Back Button — return to TesterWindow
        btnBack.addActionListener(e -> {
            if (parentWindow != null) {
                parentWindow.setVisible(true);
            }
            dispose(); // close this window
        });

        setVisible(true);
    }

    // === Clear Input Fields ===
    private void clearFields() {
        txtBugName.setText("");
        txtBugType.setText("");
        txtProjectName.setText("");
        txtStartDate.setText("");
        txtReportedBy.setText("");
        txtAssignedTo.setText("");
        cmbPriority.setSelectedIndex(0);
        cmbLevel.setSelectedIndex(0);
        cmbSeverity.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
    }

    // === Insert Bug Record into Database ===
    private void addBugToDatabase() {
        String bugName = txtBugName.getText().trim();
        String bugType = txtBugType.getText().trim();
        String projectName = txtProjectName.getText().trim();
        String startDateStr = txtStartDate.getText().trim();
        String reportedBy = txtReportedBy.getText().trim();
        String assignedTo = txtAssignedTo.getText().trim();

        if (bugName.isEmpty() || bugType.isEmpty() || projectName.isEmpty() || startDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill all mandatory fields!");
            return;
        }

        java.sql.Date sqlDate;
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
            sqlDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "❌ Invalid date format! Use yyyy-MM-dd.");
            return;
        }

        String bugPriority = cmbPriority.getSelectedItem().toString();
        String bugLevel = cmbLevel.getSelectedItem().toString();
        String severity = cmbSeverity.getSelectedItem().toString();
        String status = cmbStatus.getSelectedItem().toString();

        String insertSQL = "INSERT INTO bugs " +
                "(bug_name, bug_type, bug_priority, project_name, bug_level, start_date, reported_by, assigned_to, severity, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(insertSQL)) {

            pst.setString(1, bugName);
            pst.setString(2, bugType);
            pst.setString(3, bugPriority);
            pst.setString(4, projectName);
            pst.setString(5, bugLevel);
            pst.setDate(6, sqlDate);
            pst.setString(7, reportedBy);
            pst.setString(8, assignedTo);
            pst.setString(9, severity);
            pst.setString(10, status);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Bug added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Failed to add bug!");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Database Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddBug::new);
    }
}
