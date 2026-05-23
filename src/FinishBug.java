import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinishBug extends JFrame {

    private JComboBox<String> cbBugID, cbStatus;
    private JTextField txtBugName, txtAssignedTo, txtDateFixed;
    private JButton btnSubmit, btnBack;

    // developer parent
    private DevelopersWindow parentWindow;

    public FinishBug(DevelopersWindow parentWindow) {
        this.parentWindow = parentWindow;
        System.out.println("FinishBug: constructed with parentWindow = " + parentWindow);
        initUI();
    }

    public FinishBug() {
        this.parentWindow = null;
        System.out.println("FinishBug: constructed WITHOUT parent (for testing)");
        initUI();
    }

    private void initUI() {
        setTitle("Finish Bug");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // --- UI components ---
        JLabel lblBugID = new JLabel("Bug ID:");
        lblBugID.setBounds(50, 30, 100, 25);
        add(lblBugID);

        cbBugID = new JComboBox<>();
        cbBugID.setBounds(180, 30, 200, 25);
        add(cbBugID);

        JLabel lblBugName = new JLabel("Bug Name:");
        lblBugName.setBounds(50, 70, 100, 25);
        add(lblBugName);

        txtBugName = new JTextField();
        txtBugName.setBounds(180, 70, 200, 25);
        txtBugName.setEditable(false);
        add(txtBugName);

        JLabel lblAssignedTo = new JLabel("Assigned To:");
        lblAssignedTo.setBounds(50, 110, 100, 25);
        add(lblAssignedTo);

        txtAssignedTo = new JTextField();
        txtAssignedTo.setBounds(180, 110, 200, 25);
        txtAssignedTo.setEditable(false);
        add(txtAssignedTo);

        JLabel lblStatus = new JLabel("New Status:");
        lblStatus.setBounds(50, 150, 100, 25);
        add(lblStatus);

        cbStatus = new JComboBox<>(new String[]{"Select", "Fixed", "Closed"});
        cbStatus.setBounds(180, 150, 200, 25);
        add(cbStatus);

        JLabel lblDateFixed = new JLabel("Date Fixed:");
        lblDateFixed.setBounds(50, 190, 100, 25);
        add(lblDateFixed);

        txtDateFixed = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtDateFixed.setBounds(180, 190, 200, 25);
        txtDateFixed.setEditable(false);
        add(txtDateFixed);

        btnBack = new JButton("Back");
        btnBack.setBounds(100, 240, 100, 30);
        add(btnBack);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(250, 240, 100, 30);
        add(btnSubmit);
        btnSubmit.setEnabled(false);

        // listeners
        cbBugID.addActionListener(e -> loadBugDetails());
        cbStatus.addActionListener(e -> validateForm());
        btnSubmit.addActionListener(e -> saveBugData());

        // Back returns to parentWindow if available (and does NOT re-create it)
        btnBack.addActionListener(e -> {
            System.out.println("FinishBug: Back pressed. parentWindow = " + parentWindow);
            if (parentWindow != null) {
                parentWindow.setVisible(true);
            }
            dispose();
        });

        // load IDs initially
        loadBugIDs();
    }

    private Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/bugtrackingsystem";
            String user = "root";
            String pass = "root";
            Class.forName("com.mysql.cj.jdbc.Driver"); // ensure driver is available
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB connection failed: " + e.getMessage());
            return null;
        }
    }

    private void loadBugIDs() {
        Connection con = getConnection();
        if (con == null) {
            System.out.println("FinishBug: DB connection null in loadBugIDs");
            return;
        }
        try (PreparedStatement ps = con.prepareStatement("SELECT bug_id FROM bugs WHERE status NOT IN ('Fixed','Closed')")) {
            ResultSet rs = ps.executeQuery();
            cbBugID.removeAllItems();
            cbBugID.addItem("Select");
            while (rs.next()) {
                cbBugID.addItem(String.valueOf(rs.getInt("bug_id")));
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bug IDs: " + ex.getMessage());
        }
    }

    private void loadBugDetails() {
        String sel = (String) cbBugID.getSelectedItem();
        if (sel == null || sel.equals("Select")) {
            txtBugName.setText("");
            txtAssignedTo.setText("");
            btnSubmit.setEnabled(false);
            return;
        }
        Connection con = getConnection();
        if (con == null) return;
        try (PreparedStatement ps = con.prepareStatement("SELECT bug_name, assigned_to FROM bugs WHERE bug_id = ?")) {
            ps.setString(1, sel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtBugName.setText(rs.getString("bug_name"));
                txtAssignedTo.setText(rs.getString("assigned_to"));
            }
            con.close();
            validateForm();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bug details: " + ex.getMessage());
        }
    }

    private void validateForm() {
        boolean valid = cbBugID.getSelectedItem() != null
                && !"Select".equals(cbBugID.getSelectedItem().toString())
                && !"Select".equals(cbStatus.getSelectedItem().toString());
        btnSubmit.setEnabled(valid);
    }

    private void saveBugData() {
        String bugID = (String) cbBugID.getSelectedItem();
        String status = (String) cbStatus.getSelectedItem();
        String dateFixed = txtDateFixed.getText();

        System.out.println("FinishBug: saving bug " + bugID + " -> " + status);

        Connection con = getConnection();
        if (con == null) {
            JOptionPane.showMessageDialog(this, "No DB connection; cannot save.");
            return;
        }
        try (PreparedStatement ps = con.prepareStatement("UPDATE bugs SET status = ?, date_fixed = ? WHERE bug_id = ?")) {
            ps.setString(1, status);
            ps.setString(2, dateFixed);
            ps.setString(3, bugID);
            int rows = ps.executeUpdate();
            con.close();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Bug updated successfully.");
                // keep window open and refresh
                loadBugIDs();            // refresh available IDs
                cbBugID.setSelectedIndex(0); // move to "Select"
                cbStatus.setSelectedIndex(0);
                txtBugName.setText("");
                txtAssignedTo.setText("");
                validateForm();         // will disable submit
            } else {
                JOptionPane.showMessageDialog(this, "No record updated. Check bug id.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving bug: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FinishBug().setVisible(true));
    }
}
