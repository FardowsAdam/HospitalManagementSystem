package hospitalmanagement.ui;

import hospitalmanagement.services.StatisticsService;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StatisticsDisplay {

    public StatisticsDisplay() {
        // Create a JFrame to display the statistics
        JFrame statsFrame = new JFrame("Hospital Statistics");
        statsFrame.setSize(800, 600);
        statsFrame.setLocationRelativeTo(null); // Center the window
        statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a JPanel with BoxLayout for a clean, vertically arranged layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adding margin around the panel

        // Title for the statistics section
        JLabel titleLabel = new JLabel("Hospital Statistics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between title and content

        // Display Average Salary by Department
        panel.add(new JLabel("Average Salary by Department:"));
        ArrayList<String[]> avgSalaryData = StatisticsService.getAverageSalaryByDepartment();
        for (String[] data : avgSalaryData) {
            JLabel salaryLabel = new JLabel(data[0] + ": $" + data[1]);
            salaryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            panel.add(salaryLabel);
        }

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between sections

        // Display Star Doctor of the Week
        panel.add(new JLabel("Star Doctor of the Week:"));
        String[] starDoctor = StatisticsService.getStarDoctorOfTheWeek();
        JLabel starDoctorLabel = new JLabel(starDoctor[0] + " with " + starDoctor[1] + " appointments");
        starDoctorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(starDoctorLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between sections

        // Display Doctor with Longest Hours
        panel.add(new JLabel("Doctor with Longest Working Hours:"));
        String[] longestHours = StatisticsService.getDoctorWithLongestHours();
        JLabel longestHoursLabel = new JLabel("Doctor ID: " + longestHours[0] + ", Total Minutes: " + longestHours[1]);
        longestHoursLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(longestHoursLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between sections

        // Display Highest Bill
        panel.add(new JLabel("Highest Bill:"));
        String[] highestBill = StatisticsService.getHighestBill();
        JLabel highestBillLabel = new JLabel("Amount: $" + highestBill[0]);
        highestBillLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(highestBillLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between sections

        // Display Most Common Diagnosis
        panel.add(new JLabel("Most Common Diagnosis:"));
        String[] commonDiagnosis = StatisticsService.getMostCommonDiagnosis();
        JLabel diagnosisLabel = new JLabel(commonDiagnosis[0] + " (" + commonDiagnosis[1] + " cases)");
        diagnosisLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(diagnosisLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between sections

        // Display Youngest Patient
        panel.add(new JLabel("Youngest Patient:"));
        String[] youngestPatient = StatisticsService.youngestPatient();
        JLabel youngestPatientLabel = new JLabel("ID: " + youngestPatient[0] + ", Name: " + youngestPatient[1] + ", DOB: " + youngestPatient[2]);
        youngestPatientLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(youngestPatientLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between sections

        // Display Oldest Patient
        panel.add(new JLabel("Oldest Patient:"));
        String[] oldestPatient = StatisticsService.getOldestPatient();
        JLabel oldestPatientLabel = new JLabel("ID: " + oldestPatient[0] + ", Name: " + oldestPatient[1] + ", DOB: " + oldestPatient[2]);
        oldestPatientLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(oldestPatientLabel);

        // Add the panel to the frame and make it visible
        statsFrame.add(new JScrollPane(panel)); // Add scroll support
        statsFrame.setVisible(true);
    }
}

