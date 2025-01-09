package hospitalmanagement.ui;

import hospitalmanagement.services.AppointmentService;
import hospitalmanagement.services.PatientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateAppointmentDialog extends JDialog {

    public UpdateAppointmentDialog(int appointmentID, Runnable callback) {
        setTitle("Update Appointment Information");
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the dialog
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components

        // Labels and text fields for appointment details
        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Scheduled", "Completed", "Canceled"});
        JLabel dateLabel = new JLabel("Appointment Date:");
        JTextField dateField = new JTextField();  // Format should be "yyyy-MM-dd HH:mm:ss"
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");


        try (ResultSet rs = AppointmentService.getAppointmentByID(appointmentID)) {
            if (rs.next()) {
                statusComboBox.setSelectedItem(rs.getString("status"));
                dateField.setText(rs.getString("AppointmentDate"));
                descriptionField.setText(rs.getString("reason"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading patient data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        add(statusLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(statusComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        add(dateLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        add(descriptionLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(descriptionField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);


        // Action listener for updating the appointment
        saveButton.addActionListener(e -> {
            try {
                // Collect updated values
                String status = (String) statusComboBox.getSelectedItem();
                String Date = dateField.getText();
                String description = descriptionField.getText();

                // Validate inputs
                assert status != null;
                if (status.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the patient in the database
                AppointmentService.updateAppointment(appointmentID,status, Date, description);

                // Refresh the table
                if (callback != null) {
                    callback.run();
                }

                // Close the dialog
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action listener for canceling the dialog
        cancelButton.addActionListener(e -> {
            dispose();  // Close the dialog without making any changes
        });

        // Show the dialog
        setVisible(true);
    }


}
