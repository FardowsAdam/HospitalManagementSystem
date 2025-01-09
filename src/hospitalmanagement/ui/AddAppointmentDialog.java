package hospitalmanagement.ui;

import hospitalmanagement.services.AppointmentService;
import hospitalmanagement.services.PatientService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class AddAppointmentDialog extends JDialog {
    private JComboBox<Integer> patientComboBox;
    private JComboBox<String> specialtyComboBox;
    private JComboBox<String> timeComboBox;  // ComboBox to select time slots
    private JSpinner dateTimeSpinner;
    private JTextField reasonTextField;

    private JComboBox<String> statusComboBox;
    private JButton searchButton;
    private JButton saveButton;
    private JTable doctorTable;

    private Runnable callback; // callback to refresh the appointments table

    private final String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public AddAppointmentDialog(Runnable callback) {
        setTitle("Add Appointment");
        setSize(800, 600);
        setLocationRelativeTo(null);
        this.callback = callback;

        JPanel panel = new JPanel(new BorderLayout());

        // Top panel with form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add space between fields
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // Patient ID
        JLabel patientLabel = new JLabel("Patient ID:");
        patientComboBox = new JComboBox<>();
        populatePatientComboBox();
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(patientLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(patientComboBox, gbc);

        // Specialty
        JLabel specialtyLabel = new JLabel("Specialty:");
        specialtyComboBox = new JComboBox<>(new String[]{"Cardiology", "Neurology", "Pediatrics", "Orthopedics", "General Medicine"});
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(specialtyLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(specialtyComboBox, gbc);

        // Status
        JLabel statusLabel = new JLabel("Status:");
        statusComboBox = new JComboBox<>(new String[]{"Scheduled", "Completed", "Canceled"});
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(statusLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(statusComboBox, gbc);


        // Reason for Appointment
        JLabel reasonLabel = new JLabel("Reason for Appointment:");
        reasonTextField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(reasonLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(reasonTextField, gbc);


        // Create JLabel
        JLabel dateLabel = new JLabel("Select Date:");

// Create JSpinner for selecting date only (without time)
        dateTimeSpinner = new JSpinner(new SpinnerDateModel());

// Set the DateEditor format to only display the date (without time)
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateTimeSpinner, "yyyy-MM-dd");
        dateTimeSpinner.setEditor(editor);

        Button go=new Button("Go");
        go.addActionListener(e -> updateTimeSlots());

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(dateLabel, gbc);
        gbc.gridx=2;
        formPanel.add(go,gbc);
        gbc.gridx = 1;
        formPanel.add(dateTimeSpinner, gbc);


        // Time Slot Selector
        JLabel timeLabel = new JLabel("Select Time:");
        timeComboBox = new JComboBox<>();
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(timeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(timeComboBox, gbc);



        // Bottom panel with search button, doctor table, and save button
        JPanel actionPanel = new JPanel();
        searchButton = new JButton("Search Doctors");
        saveButton = new JButton("Save Appointment");

        searchButton.addActionListener(e -> searchAvailableDoctors());
        saveButton.addActionListener(e -> saveAppointment());

        actionPanel.add(searchButton);
        actionPanel.add(saveButton);

        // Doctor Table to display available doctors
        doctorTable = new JTable();
        JScrollPane doctorScrollPane = new JScrollPane(doctorTable);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(doctorScrollPane, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);

        add(panel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // Populate the patient combo box with patient IDs from the database
    private void populatePatientComboBox() {
        try (ResultSet rs = PatientService.getAllPatients()) {
            while (rs.next()) {
                int patientID = rs.getInt("PatientID");
                patientComboBox.addItem(patientID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search for available doctors based on specialty, date/time, day, and selected time
    private void searchAvailableDoctors() {
        String specialty = (String) specialtyComboBox.getSelectedItem();
        String selectedDay = getDayOfWeek(dateTimeSpinner);
        String selectedTime = (String) timeComboBox.getSelectedItem();
        java.util.Date appointmentDate = (java.util.Date) dateTimeSpinner.getValue();

        if (selectedTime == null || appointmentDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a day, time, and appointment date.");
            return;
        }

        try (ResultSet rs = AppointmentService.getAvailableDoctors(specialty, selectedDay, selectedTime, appointmentDate)) {
            ArrayList<String[]> doctorData = new ArrayList<>();
            while (rs.next()) {
                String doctorName = rs.getString("DoctorName");
                int doctorID = rs.getInt("DoctorID");
                doctorData.add(new String[]{String.valueOf(doctorID), doctorName});
            }

            String[] columnNames = {"Doctor ID", "Doctor Name"};
            String[][] rowData = new String[doctorData.size()][2];
            for (int i = 0; i < doctorData.size(); i++) {
                rowData[i] = doctorData.get(i);
            }

            doctorTable.setModel(new DefaultTableModel(rowData, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Save the appointment to the database
    private void saveAppointment() {
        int patientID = (Integer) patientComboBox.getSelectedItem();
        java.util.Date appointmentDate = (java.util.Date) dateTimeSpinner.getValue();
        String reason = reasonTextField.getText();
        String status = (String) statusComboBox.getSelectedItem();
        int selectedRow = doctorTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor.");
            return;
        }

        int doctorID = Integer.parseInt((String) doctorTable.getValueAt(selectedRow, 0));

        // Call the service to add the appointment
        AppointmentService.addAppointment( status, appointmentDate, reason, doctorID, patientID);

        // Notify the user and refresh the table
        JOptionPane.showMessageDialog(this, "Appointment added successfully!");
        callback.run();  // Refresh the appointments table
        dispose();
    }

    // Update time slots based on the selected day
    private void updateTimeSlots() {
        dateTimeSpinner.removeChangeListener(e -> updateTimeSlots());
        timeComboBox.removeAllItems(); // Clear existing slots

        String selectedDay = getDayOfWeek(dateTimeSpinner);
        String Speciality= (String) specialtyComboBox.getSelectedItem();

        try (ResultSet rs = AppointmentService.getScheduleByDay(selectedDay,Speciality)) {
            while (rs.next()) {
                Time startTime = rs.getTime("StartTime");
                Time endTime = rs.getTime("EndTime");

                // Generate time slots for each schedule range
                List<String> timeSlots = generateTimeSlots(startTime, endTime);
                for (String slot : timeSlots) {
                    timeComboBox.addItem(slot);
                }
            }

            if (timeComboBox.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No schedule available for the selected day.");
            }
            dateTimeSpinner.addChangeListener(e -> updateTimeSlots());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching schedule: " + e.getMessage());
        }
    }


    // Function to get the day of the week
    public static String getDayOfWeek(JSpinner dateSpinner) {
        // Extract the Date value from the JSpinner
        Date date = (Date) dateSpinner.getValue();

        // Format the Date object into the day of the week
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");  // "EEEE" for full day name (e.g., Monday)
        return sdf.format(date);  // Return the formatted day of the week
    }

    // Helper method: Generate Time Slots
    private List<String> generateTimeSlots(Time startTime, Time endTime) {
        List<String> timeSlots = new ArrayList<>();
        LocalTime start = startTime.toLocalTime();
        LocalTime end = endTime.toLocalTime();

        while (!start.isAfter(end)) {
            timeSlots.add(start.toString()); // Format: HH:mm
            start = start.plusMinutes(30);  // Increment by 30 minutes
        }
        return timeSlots;
    }

}
