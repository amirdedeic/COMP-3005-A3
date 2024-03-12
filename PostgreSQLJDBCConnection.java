import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PostgreSQLJDBCConnection {
    private static final String jdbcURL = "jdbc:postgresql://localhost:5432/COMP_3005_A3";
    private static final String username = "postgres";
    private static final String password = "password";

    public static void main(String[] args) {
        try {
            Connection conn = connect();
            getAllStudents(conn);
            addStudent(conn, "Alice", "Johnson", "alice.johnson@example.com", LocalDate.of(2023, 1, 15));
            updateStudentEmail(conn, 1, "newalice.johnson@example.com");
            deleteStudent(conn, 1);
            conn.close(); // Close the connection at the end of your operations
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection connect() throws SQLException {
        // Load PostgreSQL JDBC Driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Connect to the database
        Connection conn = DriverManager.getConnection(jdbcURL, username, password);
        if (conn != null) {
            System.out.println("Connected to PostgreSQL successfully!");
        } else {
            System.out.println("Failed to establish connection.");
        }
        return conn;
    }

    private static void getAllStudents(Connection conn) throws SQLException {
        String query = "SELECT * FROM students";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
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
        String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setDate(4, java.sql.Date.valueOf(enrollment_date));
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        }
    }

    private static void updateStudentEmail(Connection conn, int student_id, String new_email) throws SQLException {
        String query = "UPDATE students SET email = ? WHERE student_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, new_email);
            pstmt.setInt(2, student_id);
            pstmt.executeUpdate();
            System.out.println("Student email updated successfully.");
        }
    }

    private static void deleteStudent(Connection conn, int student_id) throws SQLException {
        String query = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, student_id);
            pstmt.executeUpdate();
            System.out.println("Student deleted successfully.");
        }
    }
}
// COMMENTS TO ADD CHANGES