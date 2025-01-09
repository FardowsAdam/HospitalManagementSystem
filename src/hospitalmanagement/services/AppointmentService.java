package hospitalmanagement.services;

import java.sql.*;
import java.util.Date;

public class AppointmentService {
    public static ResultSet getAllAppointments() {
        String query = "SELECT * FROM Appointment_view order by appointmentID ";
        try {
            Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error fetching Doctors: " + e.getMessage());
            return null;
        }
    }

    public static void deleteAppointment(int appointmentID) {
        String query = "DELETE FROM appointment WHERE appointmentID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, appointmentID);
            pstmt.executeUpdate();
            System.out.println("appointment deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting appointment: " + e.getMessage());
        }
    }

    public static ResultSet getAppointmentByID(int appointmentID) {
        String query = "SELECT * FROM appointment WHERE appointmentID = ?";
        try {
            Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, appointmentID); // Set the appointment ID parameter
            return pstmt.executeQuery(); // Execute the query and return the result set
        } catch (SQLException e) {
            System.out.println("Error fetching appointment by ID: " + e.getMessage());
            return null;
        }
    }

    // Method to update an appointment's status, date, and description
    public static void updateAppointment(int appointmentID, String status, String date, String description) {
        String query = "UPDATE appointment SET status = ?, appointmentDate = ?, Reason = ? WHERE appointmentID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);          // Set the status
            pstmt.setString(2, date);            // Set the appointment date
            pstmt.setString(3, description);     // Set the description
            pstmt.setInt(4, appointmentID);      // Set the appointment ID for the update
            pstmt.executeUpdate();               // Execute the update
            System.out.println("Appointment updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating appointment: " + e.getMessage());
        }
    }

    // Fetch doctors available in the selected specialty and at the requested date/time
    public static ResultSet getAvailableDoctors(String specialty, String selectedDay, String selectedTime, Date appointmentDate) {
        String query = "SELECT d.DoctorID as DoctorID, d.Fname as DoctorName " +
                "FROM doctor d " +
                "INNER JOIN doctorschedule dsched ON d.DoctorID = dsched.DoctorID " +
                "WHERE d.Specialty = ? " +
                "AND dsched.DayOfWeek = ? " +
                "AND dsched.StartTime <= ? " +
                "AND dsched.EndTime >= ? " +
                "AND NOT EXISTS (SELECT 1 FROM appointment a WHERE a.DoctorID = d.DoctorID AND a.AppointmentDate = ?)";

        try {
            Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, specialty); // Specialty
            stmt.setString(2, selectedDay); // Day (e.g., "Monday")
            stmt.setString(3, selectedTime); // Start time
            stmt.setString(4, selectedTime); // End time
            stmt.setDate(5, new java.sql.Date(appointmentDate.getTime())); // Appointment date

            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




    // This method fetches the doctor's schedule for a specific day
    public static ResultSet getScheduleByDay(String dayOfWeek,String specialty) {
        String query = "SELECT StartTime, EndTime FROM doctorschedule dsc inner join Doctor d  on dsc.DoctorID = d.DoctorID" +
                "  WHERE DayOfWeek = ? AND specialty = ? ";

        try {
            Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, dayOfWeek); // Day of the week
            stmt.setString(2, specialty); // Specialty
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add a new appointment to the database
    public static void addAppointment( String Status, Date appointmentDate, String reason, int doctorID, int patientID) {
        String query =
                "INSERT INTO Appointment ( Status, AppointmentDate, Reason, DoctorID, PatientID) " +
                        "VALUES (?, ?, ?, ?, ?)";  // Make sure no 'Amount' column is referenced

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, Status);  // Set the status
            pstmt.setTimestamp(2, new Timestamp(appointmentDate.getTime()));  // Set the appointment date and time
            pstmt.setString(3, reason);  // Set the reason for the appointment
            pstmt.setInt(4, doctorID);  // Set the doctor ID
            pstmt.setInt(5, patientID);  // Set the patient ID
            pstmt.executeUpdate();  // Execute the query to insert the appointment
            System.out.println("Appointment added successfully.");
        } catch (SQLException e) {
           // System.out.println("Error adding appointment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    }
