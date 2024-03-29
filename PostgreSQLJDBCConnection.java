import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PostgreSQLJDBCConnection {
    // Database connection parameters
    private static final String jdbcURL = "jdbc:postgresql://localhost:5432/COMP_3005_A3";
    private static final String username = "postgres";
    private static final String password = "password";

    public static void main(String[] args) {
        try {
            // Establish database connection
            Connection conn = connect();
            getAllStudents(conn); // Retrieve and display all students
            
            
            // Add a new student to the database
            // addStudent(conn, "Amir", "Dedeic", "sampleemail@gmail.com", LocalDate.of(2023, 1, 15));
            

            // Update the email of the student with ID 14
            // updateStudentEmail(conn, 15, "ajhdakjsdhakjsdhaksjd@gmail.com");
            
            
            // Delete the student with ID 15
            deleteStudent(conn, 15);
          
          
            // Close the database connection
            conn.close(); 
        } catch (SQLException e) {
            // Handle potential SQL exceptions
            e.printStackTrace();
        }
    }

    private static Connection connect() throws SQLException {
        // Attempt to load the PostgreSQL JDBC driver class
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // Handle case where JDBC driver class is not found
            e.printStackTrace();
        }
        // Establish and return a connection to the database
        Connection conn = DriverManager.getConnection(jdbcURL, username, password);
        if (conn != null) {
            System.out.println("Connected to PostgreSQL successfully!");
        } else {
            System.out.println("Failed to establish connection.");
        }
        return conn;
    }

    private static void getAllStudents(Connection conn) throws SQLException {
        // SQL query to select all records from students table
        String query = "SELECT * FROM students";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            // Iterate through the result set and print student details
            while (rs.next()) {
                System.out.println(rs.getInt("student_id") + ", " +
                        rs.getString("first_name") + ", " +
                        rs.getString("last_name") + ", " +
                        rs.getString("email") + ", " +
                        rs.getDate("enrollment_date"));
            }
        }
    }

    private static void addStudent(Connection conn, String first_name, String last_name, String email, LocalDate enrollment_date) throws SQLException {
        // SQL query to insert a new student record
        String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set parameters for the insert statement and execute
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setDate(4, java.sql.Date.valueOf(enrollment_date));
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        }
    }

    private static void updateStudentEmail(Connection conn, int student_id, String new_email) throws SQLException {
        // SQL query to update a student's email
        String query = "UPDATE students SET email = ? WHERE student_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set parameters for the update statement and execute
            pstmt.setString(1, new_email);
            pstmt.setInt(2, student_id);
            pstmt.executeUpdate();
            System.out.println("Student email updated successfully.");
        }
    }

    private static void deleteStudent(Connection conn, int student_id) throws SQLException {
        // SQL query to delete a student by ID
        String query = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the student ID parameter for the delete statement and execute
            pstmt.setInt(1, student_id);
            pstmt.executeUpdate();
            System.out.println("Student deleted successfully.");
        }
    }
}
// COMMENTS TO ADD CHANGES