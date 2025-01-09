package hospitalmanagement.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StatisticsService {
    public static ArrayList<String[]> getAverageSalaryByDepartment() {
            ArrayList<String[]> result = new ArrayList<>();
            String query = "SELECT specialty, AVG(Salary) AS AvgSalary FROM Doctor GROUP BY specialty";

            try (Connection conn = DatabaseConnection.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    String specialty = rs.getString("specialty");
                    double avgSalary = rs.getDouble("AvgSalary");
                    result.add(new String[]{specialty, String.valueOf(avgSalary)});
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return result;
        }


        public static String[] getStarDoctorOfTheWeek() {
            String[] result = new String[2]; // Doctor Name and Appointment Count
            String query = "SELECT Fname As DoctorName, COUNT(AppointmentID) AS AppointmentCount " +
                    "FROM Appointment INNER JOIN Doctor ON Appointment.DoctorID = Doctor.DoctorID " +
                    "GROUP BY Appointment.DoctorID ORDER BY AppointmentCount DESC LIMIT 1;";

            try (Connection conn = DatabaseConnection.connect()) {
                assert conn != null;
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(query)) {

                    if (rs.next()) {
                        result[0] = rs.getString("DoctorName");
                        result[1] = String.valueOf(rs.getInt("AppointmentCount"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return result;
    }


    public static String[] getDoctorWithLongestHours() {
        String[] result = new String[2];
        String query = "SELECT DoctorID, SUM(TIMESTAMPDIFF(MINUTE, StartTime, EndTime)) AS TotalMinutes " +
                "FROM DoctorSchedule GROUP BY DoctorID ORDER BY TotalMinutes DESC LIMIT 1;";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result[0] = rs.getString("DoctorID");
                result[1] = String.valueOf(rs.getInt("TotalMinutes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String[] getHighestBill() {
        String[] result = new String[3]; // Assuming BillID, PatientID, and TotalAmount
        String query = "SELECT MAX(TotalAmount) AS TotalAmount FROM Billing ";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result[0] = String.valueOf(rs.getDouble("TotalAmount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String[] getMostCommonDiagnosis() {
        String[] result = new String[2]; // Diagnosis and Count
        String query = "SELECT Diagnose, COUNT(*) AS Count " +
                "FROM MedicalRecord " +
                "GROUP BY Diagnose " +
                "ORDER BY Count DESC " +
                "LIMIT 1";


        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result[0] = rs.getString("Diagnose");
                result[1] = String.valueOf(rs.getInt("Count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String[] youngestPatient() {
        String[] result = new String[3]; // PatientID, FullName, DateOfBirth
        String query = "SELECT PatientID, Fname, Lname, DateOfBirth " +
                "FROM Patient " +
                "WHERE DateOfBirth = (SELECT MAX(DateOfBirth) FROM Patient);";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result[0] = rs.getString("PatientID"); // Patient ID
                result[1] = rs.getString("Fname") + " " + rs.getString("Lname"); // Full Name
                result[2] = rs.getString("DateOfBirth"); // Date of Birth
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String[] getOldestPatient() {
        String[] result = new String[3]; // PatientID, FullName, DateOfBirth
        String query = "SELECT PatientID, Fname, Lname, DateOfBirth " +
                "FROM Patient " +
                "WHERE DateOfBirth = (SELECT MIN(DateOfBirth) FROM Patient);";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result[0] = rs.getString("PatientID"); // Patient ID
                result[1] = rs.getString("Fname") + " " + rs.getString("Lname"); // Full Name
                result[2] = rs.getString("DateOfBirth"); // Date of Birth
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }



}
