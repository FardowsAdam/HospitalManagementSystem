Hospital Management System

Project Description

This Hospital Management System is designed to streamline the management of healthcare facilities by providing an integrated platform for handling appointments, billing, medical records, and doctor schedules. It is built with MySQL for the backend database and uses Swing for the graphical user interface (GUI). The system is intended for a single user who can manage all functionalities.

Features

Doctor Management: Add, update, and view doctor information and schedules.

Patient Management: Maintain patient records and history.

Appointment Management: Schedule, update, and view appointments.

Billing System: Generate, view, and update bills for appointments.

Medical Records: Manage and retrieve patient medical history and prescriptions.

Project Structure

The project consists of the following major components:

Database: MySQL is used for storing and managing data. The database includes the following tables:

Doctors

Patients

Appointments

Billing

Medical Records

Frontend: A Java Swing-based user interface for interacting with the system.

Backend: Java classes that handle business logic, database interactions, and GUI functionality.

Requirements

Software Requirements

Java Development Kit (JDK) 8 or higher

MySQL Server 5.7 or higher

MySQL Workbench (optional for database management)

An IDE such as IntelliJ IDEA or Eclipse

Hardware Requirements

Minimum 2 GHz processor

4 GB RAM

100 MB free disk space

Installation

Clone the repository or download the project files.

Import the SQL script (hospital_management.sql) into MySQL to set up the database.

Update the database connection settings in the Java code (e.g., username, password, database URL).

Compile and run the project using your preferred IDE.

Usage

Open the application and log in with administrator credentials.

Use the navigation menu to:

Manage doctor and patient records.

Schedule or view appointments.

Access or update billing information.

Retrieve patient medical records.

Update appointment statuses and trigger billing generation directly from the interface.

Sample Queries and Aggregations

Sample Queries

Retrieve all appointments for a specific doctor:

SELECT * FROM Appointments WHERE doctor_id = ?;

Find bills for a specific patient:

SELECT * FROM Billing WHERE patient_id = ?;

List all doctors available on a specific date:

SELECT * FROM Doctors WHERE doctor_id NOT IN (
    SELECT doctor_id FROM Appointments WHERE appointment_date = ?
);

Aggregate Functions

Calculate the total revenue generated:

SELECT SUM(amount) AS total_revenue FROM Billing;

Count the total number of appointments:

SELECT COUNT(*) AS total_appointments FROM Appointments;

Future Enhancements

Multi-user support with role-based access (e.g., admin, receptionist, doctor).

Integration with email or SMS for appointment reminders.

Adding analytics dashboards for reporting.

Mobile app interface for patients.

Contributors
Fardows Adam 
