import javax.swing.*;
import java.sql.*;

public class ProjectManagerUpdater extends javax.swing.JFrame {
    private UpdateUsers parent;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bugtrackingsystem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public ProjectManagerUpdater(UpdateUsers parent) {
        this.parent = parent;
        setTitle("Project Manager Updater");
        initComponents();
        setLocationRelativeTo(null);
        loadProjectManagers();

       jButton1.addActionListener(evt -> goBack());
        jButton2.addActionListener(evt -> updateProjectManager());
    }

    private void loadProjectManagers() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement("SELECT name FROM users WHERE role = 'Project Manager'")) {

            ResultSet rs = ps.executeQuery();
            jComboBox1.removeAllItems();
            jComboBox1.addItem("Select");

            while (rs.next()) {
                jComboBox1.addItem(rs.getString("name"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading project managers: " + ex.getMessage());
        }
    }

    private void updateProjectManager() {
        String managerName = (String) jComboBox1.getSelectedItem();
        String field = (String) jComboBox2.getSelectedItem();
        String newValue = jTextField1.getText().trim();

        if (managerName == null || field == null || managerName.equals("Select") || field.equals("Select") || newValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ Please fill all fields correctly!");
            return;
        }

        String column;
        switch (field) {
            case "Name": column = "name"; break;
            case "E-mail": column = "email"; break;
            case "Password": column = "password"; break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid field selected.");
                return;
        }

        String sql = "UPDATE users SET " + column + " = ? WHERE name = ? AND role = 'Project Manager'";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newValue);
            ps.setString(2, managerName);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Project Manager updated successfully!");
                jTextField1.setText("");
                loadProjectManagers();
            } else {
                JOptionPane.showMessageDialog(this, "⚠ No records updated — please check the name.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating Project Manager: " + ex.getMessage());
        }
    }

    private void goBack() {
        if (parent != null) {
            parent.setVisible(true);
        }
        dispose();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setText("Choose Project Manager:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel2.setText("Choose Field:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Name", "E-mail", "Password" }));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel3.setText("Enter New Value:");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jButton1.setText("Back");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14));
        jButton2.setText("Update");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, 0, 200, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, 0, 200, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        pack();
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
}
