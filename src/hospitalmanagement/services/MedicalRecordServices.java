package hospitalmanagement.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class MedicalRecordServices {

    public static ResultSet joinWithM_Record(int patientID) {
        String query = "SELECT * FROM Patient " +
                "JOIN medicalrecord ON Patient.PatientID = medicalrecord.PatientID " +
                "WHERE Patient.PatientID = ?";
        try {
            Connection conn = DatabaseConnection.connect(); // Assuming you have a database connection setup
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, patientID); // Set the patient ID in the query
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error fetching medical records: " + e.getMessage());
            return null;
        }
    }

    // Search medical record by ID
    public static ResultSet searchMedicalRecord(String patientID) {
        String query = "SELECT * FROM medicalRecord WHERE PatientID = ?";
        try {
            Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, patientID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error searching Medical Record : " + e.getMessage());
            return null;
        }
    }

    public static void updateMedicalRecord(int patientID, String date, String treatment, String diagnose) {
        String query = "UPDATE medicalRecord SET Date = ?, treatment = ?, diagnose = ? WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            pstmt.setString(1, date);
            pstmt.setString(2, treatment);
            pstmt.setString(3, diagnose);
            pstmt.setInt(4, patientID);


            // Execute the update query
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("medical record updated successfully.");
            } else {
                System.out.println("No medical record for patient found with ID: " + patientID);
            }

        } catch (SQLException e) {
            System.out.println("Error updating medical record: " + e.getMessage());
        }
    }
}
