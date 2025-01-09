package hospitalmanagement.ui;

import hospitalmanagement.services.MedicalRecordServices;


import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class updateMedicalRecordDialog extends JDialog {
    public updateMedicalRecordDialog(int patientID, Runnable refreshCallback) {
        setTitle("Update Patient Medical Record  Information");
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the dialog
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components

        // Input fields

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField(20);

        JLabel TreatmentLabel = new JLabel("Treatment:");
        JTextField treatmentField = new JTextField(20);

        JLabel DiagnoseLabel = new JLabel("Diagnose:");
        JTextField DiagnoseField = new JTextField(20);



        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Fetch patient data and pre-fill fields
        try (ResultSet rs = MedicalRecordServices.searchMedicalRecord(String.valueOf(patientID))) {
            if (rs.next()) {
                dateField.setText(rs.getString("Date"));
                treatmentField.setText(rs.getString("treatment"));
                DiagnoseField.setText(rs.getString("Diagnose"));;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading medical record data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Add components to the dialog using GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        add(dateLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        add(TreatmentLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(treatmentField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        add(DiagnoseLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(DiagnoseField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);

        // Save button action
        saveButton.addActionListener(e -> {
            try {
                // Collect updated values
                String date = dateField.getText();
                String treatment = treatmentField.getText();
                String diagnose = DiagnoseField.getText();

                // Validate inputs
                if (date.isEmpty() || treatment.isEmpty() || diagnose.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the patient in the database
                MedicalRecordServices.updateMedicalRecord(patientID, date, treatment, diagnose);

                // Refresh the table
                if (refreshCallback != null) {
                    refreshCallback.run();
                }

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
