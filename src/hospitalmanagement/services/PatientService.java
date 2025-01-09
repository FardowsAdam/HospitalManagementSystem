

package hospitalmanagement.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class PatientService {

    // Get all patients
    public static ResultSet getAllPatients() {
        String query = "SELECT * FROM Patient";
        try {
            Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error fetching patients: " + e.getMessage());
            return null;
        }
    }

    // Search patient by ID
    public static ResultSet searchPatientByID(String patientID) {
        String query = "SELECT * FROM Patient WHERE PatientID = ?";
        try {
            Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, patientID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error searching patient: " + e.getMessage());
            return null;
        }
    }

    // Delete a patient
    public static void deletePatient(int patientID) {
        String query = "DELETE FROM Patient WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, patientID);
            pstmt.executeUpdate();
            System.out.println("Patient deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting patient: " + e.getMessage());
        }
    }

    public static void addPatient(int id, String fname, String lname, String address, String gender, String phone, String dob) {
        String query = "INSERT INTO Patient (PatientID, Fname, Lname, Address, Gender, Phone, DateOfBirth) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            pstmt.setInt(1, id);             // Patient ID
            pstmt.setString(2, fname);       // First Name
            pstmt.setString(3, lname);       // Last Name
            pstmt.setString(4, address);     // Address
            pstmt.setString(5, gender);      // Gender
            pstmt.setString(6, phone);       // Phone
            pstmt.setString(7, dob);         // Date of Birth

            // Execute the query
            pstmt.executeUpdate();
            System.out.println("Patient added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding patient: " + e.getMessage());
        }
    }


    public static void updatePatient(int patientID, String Fname, String Lname, String address, String gender, String phone, String dob) {
        String query = "UPDATE Patient SET Fname = ?, Lname = ?, Address = ?, Gender = ?, Phone = ?, DateOfBirth = ? WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            pstmt.setString(1, Fname);      // First Name
            pstmt.setString(2, Lname);     // Last Name
            pstmt.setString(3, address);   // Address
            pstmt.setString(4, gender);    // Gender
            pstmt.setString(5, phone);     // Phone
            pstmt.setString(6, dob);       // Date of Birth
            pstmt.setInt(7, patientID);    // Patient ID (WHERE condition)

            // Execute the update query
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Patient updated successfully.");
            } else {
                System.out.println("No patient found with ID: " + patientID);
            }

        } catch (SQLException e) {
            System.out.println("Error updating patient: " + e.getMessage());
        }
    }





}


