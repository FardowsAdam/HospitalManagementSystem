package hospitalmanagement.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/project";  //  database name
    private static final String USER = "root";  //  MySQL username
    private static final String PASSWORD = "Fa5112421";  //  MySQL password

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();  // Detailed error output
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        connect();  // Test the connection
    }
}
