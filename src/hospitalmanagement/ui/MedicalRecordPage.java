package hospitalmanagement.ui;

import hospitalmanagement.services.MedicalRecordServices;
import hospitalmanagement.services.PatientService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MedicalRecordPage  {
    public MedicalRecordPage(int patientID) {
        // Create frame with smaller dimensions for better fit
        JFrame frame = new JFrame("Medical Record");
        frame.setSize(600, 200);  // Smaller size to avoid being too big
        frame.setLocationRelativeTo(null); // Center the window
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment
        frame.add(panel);

        // Create the JTable for displaying medical records
        JTable medicalRecordTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(medicalRecordTable);
        panel.add(scrollPane);  // Add the scroll pane to the panel

        // create button to update the medical record
        JPanel actionPanel = new JPanel();
        JButton updateButton = new JButton("Update Medical Record");
        actionPanel.add(updateButton);
        panel.add(actionPanel, BorderLayout.CENTER);

        // Call method to display medical records
        displayMedicalRecord(medicalRecordTable, patientID);



        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


        // Update button action
        updateButton.addActionListener(e -> {
            int selectedRow = medicalRecordTable.getSelectedRow();
            if (selectedRow != -1) {
                int patientId = (int) medicalRecordTable.getValueAt(selectedRow, 0);
                new updateMedicalRecordDialog(patientID, () -> displayMedicalRecord(medicalRecordTable,patientId));  // Pass the patient ID and a callback
            } else {
                JOptionPane.showMessageDialog(frame, "Please select the medical record to update.");
            }
        });
    }




    private void displayMedicalRecord(JTable medicalRecordTable, int patientID) {
        // Define table columns and initialize the model
        DefaultTableModel model = new DefaultTableModel(new Object[]{"RecordID", "PatientID", "Date", "Treatment", "Diagnose"}, 0);

        try (ResultSet rs = MedicalRecordServices.joinWithM_Record(patientID)) {
            while (rs.next()) {
                // Fetch data from ResultSet
                int recordID = rs.getInt("RecordID");
                int patientID_ = rs.getInt("PatientID");
                String date = rs.getString("Date");
                String treatment = rs.getString("Treatment");
                String diagnose = rs.getString("Diagnose");

                // Add row to the table model
                model.addRow(new Object[]{recordID, patientID_, date, treatment, diagnose});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching medical records: " + e.getMessage());
        }

        // Set model for the JTable
        medicalRecordTable.setModel(model);
    }




}
