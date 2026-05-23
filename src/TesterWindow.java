import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TesterWindow extends javax.swing.JFrame {

    private JLabel welcomeLabel, headerLabel;
    private JTextField searchField;
    private JButton searchButton, statsButton;
    private JPanel buttonPanel;

    public TesterWindow(String testerName) {
        initComponents(testerName);
        setTitle("Tester Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }


    private void initComponents(String testerName) {

        // === PANEL SETUP ===
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255)); // soft blue
        mainPanel.setLayout(new BorderLayout(10, 10));

        // === HEADER SECTION ===
        headerLabel = new JLabel("Bug Tracker - Tester Dashboard", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerLabel.setForeground(new Color(25, 25, 112));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        welcomeLabel = new JLabel("Welcome, " + testerName + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        welcomeLabel.setForeground(new Color(70, 70, 70));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.add(headerLabel);
        headerPanel.add(welcomeLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // === SEARCH BAR ===
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel searchLabel = new JLabel("🔍 Search Bugs:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(100, 149, 237));
        searchButton.setForeground(Color.WHITE);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel, BorderLayout.CENTER);

        // === BUTTON PANEL (ACTIONS) ===
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton btnAddBug = createStyledButton("Add Bug");
        JButton btnViewBug = createStyledButton("View Bugs");
        JButton btnAssignBug = createStyledButton("Assign Bug");
        JButton btnViewDevs = createStyledButton("View Developers");
        statsButton = createStyledButton("Tester Statistics");
        JButton btnLogout = createStyledButton("Log Out");

        // === ADD BUTTONS ===
        buttonPanel.add(btnAddBug);
        buttonPanel.add(btnViewBug);
        buttonPanel.add(btnAssignBug);
        buttonPanel.add(btnViewDevs);
        buttonPanel.add(statsButton);
        buttonPanel.add(btnLogout);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // === ACTIONS ===
        btnAddBug.addActionListener(e -> openAddBug());
        btnViewBug.addActionListener(e -> openViewBugs());
        btnAssignBug.addActionListener(e -> openAssignBug());
        btnViewDevs.addActionListener(e -> openViewDevelopers());
        statsButton.addActionListener(e -> showStats());
        btnLogout.addActionListener(e -> logout());

        // === SEARCH ACTION ===
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a keyword to search!");
            } else {
                searchBugs(keyword);
            }
        });

        pack();
    }

    // === BUTTON STYLE CREATOR ===
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(65, 105, 225));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 144, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225));
            }
        });
        return button;
    }

    // === ACTION HANDLERS ===
    private void openAddBug() {
        new AddBug().setVisible(true);
    }

    private void openViewBugs() {
        new ViewAllBugs().setVisible(true);
    }

    private void openAssignBug() {
        new AssignBug().setVisible(true);
    }

    private void openViewDevelopers() {
        new ViewDevs().setVisible(true);
    }

    private void showStats() {
        JOptionPane.showMessageDialog(this,
                "🧮 Tester Stats:\n\n" +
                        "Total Bugs Reported: 27\n" +
                        "Bugs Closed: 18\n" +
                        "Bugs Pending: 9",
                "Tester Statistics",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchBugs(String keyword) {
        JOptionPane.showMessageDialog(this,
                "Searching for bugs containing: " + keyword,
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE);
        // You can later connect this to a real SQL search query
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            JOptionPane.showMessageDialog(this, "Logged out successfully!");
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new TesterWindow("Tester A").setVisible(true));
    }
}
