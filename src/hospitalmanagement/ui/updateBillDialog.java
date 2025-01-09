package hospitalmanagement.ui;

import hospitalmanagement.services.BillingServices;
import hospitalmanagement.services.MedicalRecordServices;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class updateBillDialog extends JDialog{
    public updateBillDialog(int appointment) {
            setTitle("Update Billing Information");
            setSize(500, 400);
            setLocationRelativeTo(null); // Center the dialog
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components

            // Input fields
        JLabel TotalAmountLabel = new JLabel("Total Amount:");
        JTextField TotalAmountField = new JTextField(20);

        JLabel paymentStatusLabel = new JLabel("Payment Status:");
        JTextField paymentStatusField = new JTextField(20);


        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
            JTextField dateField = new JTextField(20);




            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Cancel");

            // Fetch patient data and pre-fill fields
            try (ResultSet rs = BillingServices.getBill(appointment)){
                if (rs.next()) {
                    TotalAmountField.setText(rs.getString("TotalAmount"));
                    paymentStatusField.setText(rs.getString("paymentStatus"));;
                    dateField.setText(rs.getString("PaymentDate"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error loading Bill data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Add components to the dialog using GridBagLayout
            gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
            add(TotalAmountLabel, gbc);
            gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
            add(TotalAmountField, gbc);

            gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
            add(paymentStatusLabel, gbc);
            gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
            add(paymentStatusField, gbc);

            gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
            add(dateLabel, gbc);
            gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
            add(dateField, gbc);

            gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            add(buttonPanel, gbc);

            // Save button action
            saveButton.addActionListener(e -> {
                try {
                    // Collect updated values
                    String TotalAmount = TotalAmountField.getText();
                    String paymentStatus = paymentStatusField.getText();
                    String date = dateField.getText();


                    // Validate inputs
                    if (date.isEmpty() || TotalAmount.isEmpty() || paymentStatus.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update the patient in the database
                    BillingServices.updateBill(appointment,TotalAmount,paymentStatus, date);

                    // Close the dialog
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error updating medical record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            // Cancel button action
            cancelButton.addActionListener(e -> dispose());

            setVisible(true);
        }
    }


