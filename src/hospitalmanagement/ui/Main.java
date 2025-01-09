package hospitalmanagement.ui;
import javax.swing.*;
import java.awt.*;


class MainPage {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hospital Management System");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);  // Center the window

        // Create Main Panel with GridBagLayout for more control
        JPanel panel = new BackgroundPanel();
        panel.setLayout(new GridBagLayout());  // Use GridBagLayout for flexible positioning
        panel.setBackground(new Color(240, 240, 240));  // Light grey background

        // GridBagConstraints for flexible layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;



        frame.setLocationRelativeTo(null);

        // Create Buttons for Navigation with icons
        JButton patientButton = createNavButton("Manage Patients", "src/hospitalmanagement/images/pics/Screenshot 2024-12-03 000909.png");
        JButton doctorButton = createNavButton("Manage Doctors", "src/hospitalmanagement/images/pics/Screenshot 2024-12-03 001000.png");
        JButton appointmentButton = createNavButton("Manage Appointments", "src/hospitalmanagement/images/pics/Screenshot 2024-12-03 001035.png");
        JButton staticsButton= createNavButton("Statics","src/hospitalmanagement/images/pics/Screenshot 2024-12-06 163829.png");
        // Add buttons to panel with some spacing
        gbc.insets = new Insets(10, 10, 10, 10);  // Add padding between buttons
        panel.add(patientButton, gbc);
        gbc.gridy++;
        panel.add(doctorButton, gbc);
        gbc.gridy++;
        panel.add(appointmentButton, gbc);
        gbc.gridy++;
        panel.add(staticsButton, gbc);
        // Add panel to the frame
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add button actions (to navigate to different pages)
        patientButton.addActionListener(e ->
            new PatientPage());

        doctorButton.addActionListener(e ->
            new DoctorPage());

        appointmentButton.addActionListener(e ->
            new AppointmentPage());

        staticsButton.addActionListener(e -> new StatisticsDisplay());

    }

    // Method to create styled navigation buttons
    private static JButton createNavButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));  // Bigger font size
        button.setForeground(Color.black);  // Button text color
        button.setBackground(new Color(234, 231, 231));  // Material blue color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));  // Soft borders with padding

        // Hover effect (change background color when mouse enters)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 182, 246));  // Lighter blue
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(238, 234, 239));  // Original color
            }
        });

        // Set button icon if provided
        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(iconPath);
            button.setIcon(new ImageIcon(icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        }

        button.setPreferredSize(new Dimension(300, 60));  // Button size
        button.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center button
        return button;
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel() {
        try {
            // Load background image
            backgroundImage = new ImageIcon("src/hospitalmanagement/images/pics/Screenshot 2024-12-03 001200.png").getImage();
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // If image is not loaded, draw a light background color as fallback
            g.setColor(new Color(240, 240, 240));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
