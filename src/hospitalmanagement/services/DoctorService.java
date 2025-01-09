package hospitalmanagement.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorService {

    public static ResultSet getAllDoctors() {
        String query = "SELECT * FROM Doctor";
        try {
            Connection conn = DatabaseConnection.connect();
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error fetching Doctors: " + e.getMessage());
            return null;
        }
    }

    // Search Doctor by ID
    public static ResultSet searchDoctorByID(String doctorID) {
        String query = "SELECT * FROM Doctor WHERE DoctorID = ?";
        try {
            Connection conn = DatabaseConnection.connect();
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, doctorID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error searching Doctor: " + e.getMessage());
            return null;
        }
    }

    // Delete a Doctor
    public static void deleteDoctor(int doctorID) {
        String query = "DELETE FROM Doctor WHERE DoctorID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, doctorID);
            pstmt.executeUpdate();
            System.out.println("Doctor deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting Doctor: " + e.getMessage());
        }
    }

    public static void addDoctor(int id, String fname, String lname, String speciality, String Phone, double Salary, String Email) {
        String query = "INSERT INTO Doctor (DoctorID, Fname, Lname, speciality, Phone, Salary,Email) Values (?, ?, ?, ?, ?, ?,?)";
        try (Connection conn = DatabaseConnection.connect()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {

                // Set parameters for the query
                pstmt.setInt(1, id);             // Doctor ID
                pstmt.setString(2, fname);       // First Name
                pstmt.setString(3, lname);       // Last Name
                pstmt.setString(4, speciality);     // speciality
                pstmt.setString(5, Phone);      // phone
                pstmt.setDouble(6, Salary);       // Salary
                pstmt.setString(7, Email);         //  Email
                // Execute the query
                pstmt.executeUpdate();
                System.out.println("Doctor added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding Doctor: " + e.getMessage());
        }
    }


    public static void updateDoctor(int id, String fname, String lname, String speciality, String Phone, double Salary, String Email) {
        String query = "UPDATE Doctor SET Fname = ?, Lname = ?, Specialty = ?, Phone = ?, Salary = ?, Email = ?  WHERE DoctorID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the query

            pstmt.setString(1, fname);       // First Name
            pstmt.setString(2, lname);       // Last Name
            pstmt.setString(3, speciality);     // speciality
            pstmt.setString(4, Phone);      // phone
            pstmt.setDouble(5, Salary);       // Salary
            pstmt.setString(6, Email);
            pstmt.setInt(7, id);             // Doctor ID//  Email
            // Execute the update query
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Doctor updated successfully.");
            } else {
                System.out.println("No Doctor found with ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println("Error updating Doctor: " + e.getMessage());
        }
    }



    public static ResultSet getDoctorAvailability(int doctorID) {
        String query = "SELECT DayOfWeek , StartTime, EndTime FROM doctorschedule WHERE DoctorID = ? ORDER BY FIELD(DayOfWeek , 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday')";
        try {
            Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, doctorID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error fetching Doctor Availability: " + e.getMessage());
            return null;
        }
    }


    public static ResultSet getDoctorName(int doctorID) {
        String query = "select  Fname , Lname From Doctor where DoctorID = ? ";
        try {
            Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, doctorID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error fetching Doctor Availability: " + e.getMessage());
            return null;
        }
    }
}
