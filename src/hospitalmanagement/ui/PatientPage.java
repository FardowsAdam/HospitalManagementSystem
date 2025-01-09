package hospitalmanagement.ui;

import hospitalmanagement.services.PatientService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientPage {

    public PatientPage() {
        JFrame frame = new JFrame("Patient Management");
        frame.setSize(800, 600);

        // Main panel for the page
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Table to display patients
        JTable patientTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(patientTable);

        // Fetch and display all patients initially
        displayAllPatients(patientTable);

        // Top panel for search
        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("Search by ID:");
        JTextField searchField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear");
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        // Bottom panel for actions
        JPanel actionPanel = new JPanel();
        JButton addButton = new JButton("Add Patient");
        JButton updateButton = new JButton("Update Patient");
        JButton deleteButton = new JButton("Delete Patient");
        JButton MedicalRecord = new JButton("Patient Medical Record");
        actionPanel.add(addButton);
        actionPanel.add(updateButton);
        actionPanel.add(deleteButton);
        actionPanel.add(MedicalRecord);

        // Add components to the main panel
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        // Search button action
        searchButton.addActionListener(e -> {
            String patientID = searchField.getText();
            if (!patientID.isEmpty()) {
                searchPatientByID(patientTable, patientID);
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a patient ID.");
            }
        });

        // Clear button action
        clearButton.addActionListener(e -> {
            searchField.setText("");  // Clear the search field
            displayAllPatients(patientTable);  // Restore the full table
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow != -1) {
                int patientID = (int) patientTable.getValueAt(selectedRow, 0);
                deletePatient(patientID);
                displayAllPatients(patientTable);  // Refresh table
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a patient to delete.");
            }
        });

        // Add button action
        addButton.addActionListener(e -> {
            new AddPatientDialog(() -> displayAllPatients(patientTable));  // Pass a callback to refresh the table
        });

        // Update button action
        updateButton.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow != -1) {
                int patientID = (int) patientTable.getValueAt(selectedRow, 0);
                new UpdatePatientDialog(patientID, () -> displayAllPatients(patientTable));  // Pass the patient ID and a callback
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a patient to update.");
            }
        });



        MedicalRecord.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow != -1) {
                int patientID = (int) patientTable.getValueAt(selectedRow, 0);
                new MedicalRecordPage(patientID);  // Pass the patient ID and a callback
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a patient to Display their medical record.");
            }
        });
    }




    // Display all patients in the table
    private void displayAllPatients(JTable table) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"PatientID", "Fname","Lname", "Address", "Gender","Phone", "DateOfBirth"}, 0);
        try (ResultSet rs = PatientService.getAllPatients()) {
            while (rs.next()) {
                int patientID = rs.getInt("PatientID");
                String Fname = rs.getString("Fname");
                String Lname = rs.getString("Lname");
                String address= rs.getString("Address");
                String gender = rs.getString("Gender");
                String phone = rs.getString("Phone");
                String dob = rs.getString("DateOfBirth");
                model.addRow(new Object[]{patientID, Fname,Lname, address, gender, phone, dob});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setModel(model);
    }

    // Search for a patient by ID
    private void searchPatientByID(JTable table, String patientID) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"PatientID", "Fname","Lname", "Address", "Gender","Phone", "DateOfBirth"}, 0);
        try (ResultSet rs = PatientService.searchPatientByID(patientID)) {
            while (rs.next()) {
                int  patientIDRequired = rs.getInt("PatientID");
                String Fname = rs.getString("Fname");
                String Lname = rs.getString("Lname");
                String address= rs.getString("Address");
                String gender = rs.getString("Gender");
                String phone = rs.getString("Phone");
                String dob = rs.getString("DateOfBirth");
                model.addRow(new Object[]{patientIDRequired, Fname,Lname, address, gender, phone, dob});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setModel(model);
    }

    // Delete a patient by ID
    private void deletePatient(int patientID) {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this patient?", "Delete Patient", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            PatientService.deletePatient(patientID);
        }
    }
}

