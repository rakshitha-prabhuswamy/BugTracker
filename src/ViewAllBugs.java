import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class ViewAllBugs extends javax.swing.JFrame {
    private AdminWindow adminParent;
    private DevelopersWindow devParent;
    private DefaultTableModel model;

    // Constructor for AdminWindow
    public ViewAllBugs(AdminWindow parent) {
        this.adminParent = parent;
        setTitle("View All Bugs - Admin");
        initComponents();
        setLocationRelativeTo(null);
        loadBugData();
    }

    // Constructor for DeveloperWindow
    public ViewAllBugs(DevelopersWindow parent) {
        this.devParent = parent;
        setTitle("View All Bugs - Developer");
        initComponents();
        setLocationRelativeTo(null);
        loadBugData();
    }

    // Default constructor (for independent run)
    public ViewAllBugs() {
        setTitle("View All Bugs");
        initComponents();
        setLocationRelativeTo(null);
        loadBugData();
    }

    /** Load bug data from database **/
    private void loadBugData() {
        String[] columnNames = {
            "Bug ID", "Bug Name", "Bug Type", "Bug Priority",
            "Project Name", "Bug Level", "Start Date",
            "Reported By", "Assigned To", "Severity", "Status"
        };

        model = new DefaultTableModel(columnNames, 0);
        jTable1.setModel(model);

        try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bugtrackingsystem", "root", "root");
             PreparedStatement pst = con.prepareStatement(
                     "SELECT bug_id, bug_name, bug_type, bug_priority, project_name, bug_level, start_date, reported_by, assigned_to, severity, status FROM bugs");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("bug_id"),
                    rs.getString("bug_name"),
                    rs.getString("bug_type"),
                    rs.getString("bug_priority"),
                    rs.getString("project_name"),
                    rs.getString("bug_level"),
                    rs.getDate("start_date"),
                    rs.getString("reported_by"),
                    rs.getString("assigned_to"),
                    rs.getString("severity"),
                    rs.getString("status")
                };
                model.addRow(row);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading bug data: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(950, 500);

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 13));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Bug ID", "Bug Name", "Bug Type", "Bug Priority",
                "Project Name", "Bug Level", "Start Date",
                "Reported By", "Assigned To", "Severity", "Status"
            }
        ));

        // 🎨 Add simple color highlighting by status
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                String status = (String) table.getValueAt(row, 10); // Status column
                if (!isSelected) {
                    if (status != null) {
                        if (status.equalsIgnoreCase("Critical")) {
                            c.setBackground(new Color(255, 179, 179)); // Light red
                        } else if (status.equalsIgnoreCase("Fixed")) {
                            c.setBackground(new Color(179, 255, 179)); // Light green
                        } else {
                            c.setBackground(Color.white);
                        }
                    } else {
                        c.setBackground(Color.white);
                    }
                }
                return c;
            }
        });

        jScrollPane1.setViewportView(jTable1);

        backButton.setFont(new java.awt.Font("Tahoma", 1, 14));
        backButton.setText("Back");
        backButton.addActionListener(evt -> backButtonActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addGap(400, 400, 400)
                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(backButton)
                    .addGap(0, 20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        if (adminParent != null) {
            adminParent.setVisible(true);
        } else if (devParent != null) {
            devParent.setVisible(true);
        }
    }

    // MAIN METHOD — for independent testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewAllBugs().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton backButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration
}
