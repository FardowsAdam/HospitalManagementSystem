package hospitalmanagement.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingServices {


    public static  ResultSet getBill(int appointmentID){
        String query = "SELECT * FROM billing where appointmentID = ? ";
        try {
            Connection conn = DatabaseConnection.connect();
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, appointmentID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error fetching Doctors: " + e.getMessage());
            return null;
        }
    }


    public static void updateBill(int appointment ,String totalAmount, String paymentStatus, String date) {
        String query = "UPDATE Billing SET totalAmount = ?, paymentStatus = ?, PaymentDate = ? WHERE appointmentID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            pstmt.setString(1, totalAmount);
            pstmt.setString(2, paymentStatus);
            pstmt.setString(3, date);
            pstmt.setInt(4, appointment);


            // Execute the update query
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Bill updated successfully.");
            } else {
                System.out.println("No Bill for specified appointment  found");
            }

        } catch (SQLException e) {
            System.out.println("Error updating bill: " + e.getMessage());
        }
    }

}
