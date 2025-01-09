package hospitalmanagement.ui;

import hospitalmanagement.services.DoctorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;



 class DisplayDoctorSchaduleDialog extends JDialog {
    public DisplayDoctorSchaduleDialog(int doctorID) {
        setTitle("Doctor Availability Schedule");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Use BorderLayout for organization

        // Fetch and display doctor's name
        String doctorName = getDoctorName(doctorID);
        JLabel doctorNameLabel = new JLabel("Doctor: " + doctorName, SwingConstants.CENTER);
        doctorNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(doctorNameLabel, BorderLayout.NORTH);

        // Table to display the schedule
        JTable availabilityTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(availabilityTable);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch and display the availability
        displayDoctorAvailability(availabilityTable, doctorID);

        setVisible(true);
    }

    // Method to fetch the doctor's full name from the database
    private String getDoctorName(int doctorID) {
        String fname = "";
        String lname = "";
        try (ResultSet rs = DoctorService.getDoctorName(doctorID)) {
            if (rs.next()) {
                fname = rs.getString("Fname");
                lname = rs.getString("Lname");
            } else {
                JOptionPane.showMessageDialog(this, "Doctor not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching doctor name: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return fname + " " + lname;
    }

    // Method to fetch and display the doctor's availability
    private void displayDoctorAvailability(JTable table, int doctorID) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"DayOfWeek", "StartTime", "EndTime"}, 0);
        try (ResultSet rs = DoctorService.getDoctorAvailability(doctorID)) {
            while (rs.next()) {
                String day = rs.getString("DayOfWeek");
                String startTime = rs.getString("StartTime");
                String endTime = rs.getString("EndTime");
                model.addRow(new Object[]{day, startTime, endTime});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching schedule" );
        }
        table.setModel(model);
    }

}