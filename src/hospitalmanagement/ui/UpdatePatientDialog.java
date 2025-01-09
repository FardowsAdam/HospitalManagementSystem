package hospitalmanagement.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import hospitalmanagement.services.PatientService;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdatePatientDialog extends JDialog {

    public UpdatePatientDialog(int patientID, Runnable refreshCallback) {
        setTitle("Update Patient Information");
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the dialog
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components

        // Input fields
        JLabel FnameLabel = new JLabel("First Name:");
        JTextField FnameField = new JTextField(20);

        JLabel LnameLabel = new JLabel("Last Name:");
        JTextField LnameField = new JTextField(20);

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField(20);

        JLabel genderLabel = new JLabel("Gender:");
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"M", "F"});

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(20);

        JLabel dobLabel = new JLabel("Date of Birth (YYYY-MM-DD):");
        JTextField dobField = new JTextField(20);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Fetch patient data and pre-fill fields
        try (ResultSet rs = PatientService.searchPatientByID(String.valueOf(patientID))) {
            if (rs.next()) {
                FnameField.setText(rs.getString("Fname"));
                LnameField.setText(rs.getString("Lname"));
                addressField.setText(rs.getString("Address"));
                genderComboBox.setSelectedItem(rs.getString("Gender"));
                phoneField.setText(rs.getString("Phone"));
                dobField.setText(rs.getString("DateOfBirth"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading patient data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Add components to the dialog using GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        add(FnameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(FnameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        add(LnameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(LnameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        add(addressLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        add(genderLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        add(genderComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST;
        add(dobLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        add(dobField, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);

        // Save button action
        saveButton.addActionListener(e -> {
            try {
                // Collect updated values
                String Fname = FnameField.getText();
                String Lname = LnameField.getText();
                String address = addressField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                String phone = phoneField.getText();
                String dob = dobField.getText();

                // Validate inputs
                if (Fname.isEmpty() || Lname.isEmpty() || address.isEmpty() || phone.isEmpty() || dob.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the patient in the database
                PatientService.updatePatient(patientID, Fname, Lname, address, gender, phone, dob);

                // Refresh the table
                if (refreshCallback != null) {
                    refreshCallback.run();
                }

                // Close the dialog
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel button action
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}


