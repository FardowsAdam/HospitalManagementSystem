package hospitalmanagement.ui;

import hospitalmanagement.services.BillingServices;
import hospitalmanagement.services.MedicalRecordServices;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class billCatalog {
    public billCatalog(int appointmentID){

        // Create frame with smaller dimensions for better fit
        JFrame frame = new JFrame("Billing");
        frame.setSize(600, 200);  // Smaller size to avoid being too big
        frame.setLocationRelativeTo(null); // Center the window
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment
        frame.add(panel);

        // Create the JTable for displaying medical records
        JTable BillTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(BillTable);
        panel.add(scrollPane);  // Add the scroll pane to the panel

        // create button to update the medical record
        JPanel actionPanel = new JPanel();
        JButton updateButton = new JButton("Update Bill");
        actionPanel.add(updateButton);
        panel.add(actionPanel, BorderLayout.CENTER);

        // Call method to display medical records
        DisplayBill(BillTable, appointmentID);



        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


        // Update button action
        updateButton.addActionListener(e -> {
            int selectedRow = BillTable.getSelectedRow();
            if (selectedRow != -1) {
                new updateBillDialog(appointmentID);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select the Bill to update.");
            }
        });
    }




    private void DisplayBill(JTable BillTable , int appointment) {
        // Define table columns and initialize the model
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Total Amount", "Payment Status", "Payment Date"}, 0);

        try (ResultSet rs = BillingServices.getBill(appointment)) {
            while (rs.next()) {
                // Fetch data from ResultSet
                String TotalAmount = rs.getString("TotalAmount");
                String PaymentStatus = rs.getString("PaymentStatus");
                String date = rs.getString("PaymentDate");
                // Add row to the table model
                model.addRow(new Object[]{TotalAmount,PaymentStatus, date});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching The bill: " + e.getMessage());
        }

        // Set model for the JTable
        BillTable.setModel(model);
    }



}
