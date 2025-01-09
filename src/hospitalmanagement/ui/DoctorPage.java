package hospitalmanagement.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import hospitalmanagement.services.DoctorService;

public class DoctorPage extends JFrame {
    public DoctorPage() {
        JFrame frame = new JFrame("Doctor Management");
        frame.setSize(800, 600);

        // Main panel for the page
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Table to display patients
        JTable DoctorTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(DoctorTable);

        // Fetch and display all patients initially
        displayAllDoctor(DoctorTable);

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
        JButton addButton = new JButton("Add Doctor");
        JButton updateButton = new JButton("Update Doctor");
        JButton deleteButton = new JButton("Delete Doctor");
        JButton doctorScheduleButton = new JButton("Doctor’s Schedule");
        actionPanel.add(addButton);
        actionPanel.add(updateButton);
        actionPanel.add(deleteButton);
        actionPanel.add(doctorScheduleButton);

        // Add components to the main panel
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        // Search button action
        searchButton.addActionListener(e -> {
            String DoctorID = searchField.getText();
            if (!DoctorID.isEmpty()) {
                searchDoctorByID(DoctorTable, DoctorID);
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a Doctor ID.");
            }
        });

        // Clear button action
        clearButton.addActionListener(e -> {
            searchField.setText("");  // Clear the search field
            displayAllDoctor(DoctorTable);  // Restore the full table
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            int selectedRow = DoctorTable.getSelectedRow();
            if (selectedRow != -1) {
                int DoctorID = (int) DoctorTable.getValueAt(selectedRow, 0);
                deleteDoctor(DoctorID);
                displayAllDoctor(DoctorTable);  // Refresh table
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a Doctor to delete.");
            }
        });

        // Add button action
        addButton.addActionListener(e -> {
            new AddDoctorDialog(() -> displayAllDoctor(DoctorTable));  // Pass a callback to refresh the table
        });

        // Update button action
        updateButton.addActionListener(e -> {
            int selectedRow = DoctorTable.getSelectedRow();
            if (selectedRow != -1) {
                int doctorID = (int) DoctorTable.getValueAt(selectedRow, 0);
                new UpdateDoctorDialog(doctorID, () -> displayAllDoctor(DoctorTable));  // Pass the patient ID and a callback
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a doctor to update.");
            }
        });

        doctorScheduleButton.addActionListener(e -> {
            int selectedRow = DoctorTable.getSelectedRow();
            if (selectedRow != -1) {
                int doctorID = (int) DoctorTable.getValueAt(selectedRow, 0);
                new DisplayDoctorSchaduleDialog(doctorID);  // Pass the patient ID and a callback
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a doctor to display their schedule.");
            }
        });



    }




    // Display all doctors in the table
    private void displayAllDoctor(JTable table) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"DoctorID", "Fname","Lname", "Speciality","Phone","Salary", "Email"}, 0);
        try (ResultSet rs = DoctorService.getAllDoctors()) {
            while (rs.next()) {
                int DoctorID = rs.getInt("DoctorID");
                String Fname = rs.getString("Fname");
                String Lname = rs.getString("Lname");
                String Speciality= rs.getString("Specialty");
                String phone = rs.getString("Phone");
                String Salary = String.valueOf(rs.getDouble("Salary"));
                String Email = rs.getString("Email");
                model.addRow(new Object[]{DoctorID, Fname,Lname, Speciality,  phone,Salary, Email});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setModel(model);
    }

    // Search for a patient by ID
    private void searchDoctorByID(JTable table, String DoctorId) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"DoctorID", "Fname","Lname", "Speciality","Phone","Salary", "Email"}, 0);
        try (ResultSet rs = DoctorService.searchDoctorByID(DoctorId)) {
            while (rs.next()) {
                int DoctorID = rs.getInt("DoctorID");
                String Fname = rs.getString("Fname");
                String Lname = rs.getString("Lname");
                String Speciality= rs.getString("Specialty");
                String phone = rs.getString("Phone");
                String Salary = String.valueOf(rs.getDouble("Salary"));
                String Email = rs.getString("Email");
                model.addRow(new Object[]{DoctorID, Fname,Lname, Speciality,  phone,Salary, Email});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setModel(model);
    }

    // Delete a patient by ID
    private void deleteDoctor(int DoctorID) {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this Doctor?", "Delete Doctor", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            DoctorService.deleteDoctor(DoctorID);
        }
    }


}
