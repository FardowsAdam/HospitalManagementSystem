
package hospitalmanagement.ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

import hospitalmanagement.services.AppointmentService;
import hospitalmanagement.services.PatientService;

public class AppointmentPage extends JFrame {

        public AppointmentPage() {
            setTitle("Manage Appointments");
            setSize(900, 600);
            setLocationRelativeTo(null);

            // Main panel
            JPanel panel = new JPanel(new BorderLayout());

            // Table to display appointments
            JTable appointmentTable = new JTable();
            JScrollPane scrollPane = new JScrollPane(appointmentTable);

            // Fetch and display appointments
            displayAppointments(appointmentTable);

            // Top panel for actions
            JPanel actionPanel = new JPanel();
            JButton addButton = new JButton("Add Appointment");
            JButton updateButton = new JButton("Update Appointment");
            JButton deleteButton = new JButton("Delete Appointment");
            JButton viewBillButton =new JButton("View Bill");
            JButton refreshButton = new JButton("Refresh");
            actionPanel.add(addButton);
            actionPanel.add(updateButton);
            actionPanel.add(deleteButton);
            actionPanel.add(viewBillButton);
            actionPanel.add(refreshButton);



            panel.add(scrollPane, BorderLayout.CENTER);
            panel.add(actionPanel, BorderLayout.SOUTH);

            add(panel);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);

            // Button actions
            addButton.addActionListener(e -> addAppointment(() -> displayAppointments(appointmentTable)));
            updateButton.addActionListener(e -> updateAppointment(appointmentTable, () -> displayAppointments(appointmentTable)));
            deleteButton.addActionListener(e -> deleteAppointment(appointmentTable, () -> displayAppointments(appointmentTable)));

            // view button action
            viewBillButton.addActionListener(e -> {
                int selectedRow = appointmentTable.getSelectedRow();
                if (selectedRow != -1) {
                    int appointmentID = (int) appointmentTable.getValueAt(selectedRow, 0);
                    new billCatalog(appointmentID);
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a patient to update.");
                }
            });
            refreshButton.addActionListener(e -> displayAppointments(appointmentTable));
        }

        private void displayAppointments(JTable table) {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Status", "AppointmentDate", "Reason", "Doctor", "Patient"}, 0);
            try (ResultSet rs = AppointmentService.getAllAppointments()) {
                while (rs.next()) {
                    int id = rs.getInt("AppointmentID");
                    String status = rs.getString("Status");
                    String dateTime = rs.getString("AppointmentDate");
                    String reason = rs.getString("Reason");
                    String doctor = rs.getString("doctor");
                    String patient = rs.getString("patient");

                    model.addRow(new Object[]{id, status, dateTime, reason, doctor, patient});
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            table.setModel(model);
        }

        private void addAppointment(Runnable callback) {
            new AddAppointmentDialog(callback);
        }

        private void updateAppointment(JTable table, Runnable callback) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int appointmentID = (int) table.getValueAt(selectedRow, 0);
                new UpdateAppointmentDialog(appointmentID, callback);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment to update.");
            }
        }

        private void deleteAppointment(JTable table, Runnable callback) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this patient?", "Delete Patient", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int appointmentID = (int) table.getValueAt(selectedRow, 0);
                    AppointmentService.deleteAppointment(appointmentID);
                }
                callback.run();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment to delete.");
            }
        }


}
