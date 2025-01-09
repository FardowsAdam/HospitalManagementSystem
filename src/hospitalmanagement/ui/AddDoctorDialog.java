package hospitalmanagement.ui;

import hospitalmanagement.services.DoctorService;
import hospitalmanagement.services.PatientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDoctorDialog extends JDialog {

    public AddDoctorDialog(Runnable refreshCallback) {
        setTitle("Add New Doctor");
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the dialog
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components

        // Input fields
        JLabel idLabel = new JLabel("Doctor ID:");
        JTextField idField = new JTextField(20);

        JLabel FnameLabel = new JLabel("First Name:");
        JTextField FnameField = new JTextField(20);

        JLabel LnameLabel = new JLabel("Last Name:");
        JTextField LnameField = new JTextField(20);

        JLabel SpecialtyLabel = new JLabel("Specialty:");
        JTextField SpecialtyField = new JTextField(20);

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(20);

        JLabel salaryLabel = new JLabel("Salary:");
        JTextField salaryField = new JTextField(20);

        JLabel EmailLabel = new JLabel("Email:");
        JTextField EmailField = new JTextField(20);


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
        add(SpecialtyLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        add(SpecialtyField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST;
        add(salaryLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        add(salaryField, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.EAST;
        add(EmailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.anchor = GridBagConstraints.WEST;
        add(EmailField, gbc);


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
                String specialty = SpecialtyField.getText();
                String phone = phoneField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                String email = EmailField.getText();

                // Validate input
                if (Fname.isEmpty() || Lname.isEmpty() || specialty.isEmpty() || phone.isEmpty()  || email.isEmpty()) {
                    JOptionPane.showMessageDialog(AddDoctorDialog.this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Call the service to add the patient
                DoctorService.addDoctor(id, Fname, Lname, specialty, phone,salary, email);

                // Refresh the Doctor table
                if (refreshCallback != null) {
                    refreshCallback.run();
                }

                // Close the dialog
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AddDoctorDialog.this, "Please enter a valid numeric ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AddDoctorDialog.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel button action
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}



