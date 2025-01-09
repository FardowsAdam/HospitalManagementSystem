package hospitalmanagement.ui;

import hospitalmanagement.services.PatientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPatientDialog extends JDialog {

    public AddPatientDialog(Runnable refreshCallback) {
        setTitle("Add New Patient");
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the dialog
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components

        // Input fields
        JLabel idLabel = new JLabel("Patient ID:");
        JTextField idField = new JTextField(20);

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

        // Layout and add components with GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        add(FnameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(FnameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        add(LnameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(LnameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        add(addressLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        add(genderLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        add(genderComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST;
        add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.EAST;
        add(dobLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.anchor = GridBagConstraints.WEST;
        add(dobField, gbc);

        // Add Save and Cancel buttons
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(); // Use a panel for buttons for better layout
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);

        // Save button action
        saveButton.addActionListener(e -> {
            try {
                // Collect input values
                int id = Integer.parseInt(idField.getText());
                String Fname = FnameField.getText();
                String Lname = LnameField.getText();
                String address = addressField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                String phone = phoneField.getText();
                String dob = dobField.getText();

                // Validate input
                if (Fname.isEmpty() || Lname.isEmpty() || address.isEmpty() || phone.isEmpty() || dob.isEmpty()) {
                    JOptionPane.showMessageDialog(AddPatientDialog.this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Call the service to add the patient
                PatientService.addPatient(id, Fname, Lname, address, gender, phone, dob);

                // Refresh the patient table
                if (refreshCallback != null) {
                    refreshCallback.run();
                }

                // Close the dialog
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AddPatientDialog.this, "Please enter a valid numeric ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AddPatientDialog.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel button action
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}


