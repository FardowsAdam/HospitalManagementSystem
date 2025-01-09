package hospitalmanagement.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import hospitalmanagement.services.DoctorService;
import hospitalmanagement.services.PatientService;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class UpdateDoctorDialog extends JDialog {

    public UpdateDoctorDialog(int DoctorID, Runnable refreshCallback) {
        setTitle("Update Doctor Information");
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

        JLabel specialatyLabel = new JLabel("speciality:");
        JTextField specialityField = new JTextField(20);

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(20);

        JLabel salaryLabel = new JLabel("salary:");
        JTextField salaryField = new JTextField(20);


        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Fetch patient data and pre-fill fields
        try (ResultSet rs = DoctorService.searchDoctorByID(String.valueOf(DoctorID))) {
            if (rs.next()) {
                FnameField.setText(rs.getString("Fname"));
                LnameField.setText(rs.getString("Lname"));
                specialityField.setText(rs.getString("specialty"));
                phoneField.setText(rs.getString("Phone"));
                salaryField.setText(rs.getString("salary"));
                emailField.setText(rs.getString("Email"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading doctor data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        add(specialatyLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(specialityField, gbc);


        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        add(salaryLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        add(salaryField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST;
        add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);


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
                String speciality = specialityField.getText();
                String phone = phoneField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                String email = emailField.getText();

                // Validate inputs
                if (Fname.isEmpty() || Lname.isEmpty() || speciality.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the patient in the database
                DoctorService.updateDoctor(DoctorID, Fname, Lname, speciality, phone, salary,email);

                // Refresh the table
                if (refreshCallback != null) {
                    refreshCallback.run();
                }

                // Close the dialog
                dispose();
            } catch (Exception ex) {
            System.out.println(ex.getMessage()); // Debug the exception
            if (ex.getMessage().contains("Check constraint 'check_phone_number_length'is violated")) {
                JOptionPane.showMessageDialog(this, "Phone number length is invalid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "Phone number length is invalid!", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }

    });

        // Cancel button action
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}



