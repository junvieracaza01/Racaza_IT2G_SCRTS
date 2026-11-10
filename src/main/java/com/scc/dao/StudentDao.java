package com.scc.dao;

import com.scc.model.Student;
import java.sql.*;

public class StudentDao {

    public boolean addStudent(int userId, String studentNumber,
                              String firstName, String lastName,
                              String course, String yearLevel,
                              String section, String email) {

        String sql = """
                INSERT INTO students
                (user_id, student_number, first_name, last_name,
                 course, year_level, section, email)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, studentNumber);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, course);
            ps.setString(6, yearLevel);
            ps.setString(7, section);
            ps.setString(8, email);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Student registration error: "
                    + e.getMessage());
            return false;
        }
    }

    public Student findStudent(int studentId) {

        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapStudent(rs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Student findByUserId(int userId) {

        String sql = "SELECT * FROM students WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapStudent(rs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void displayStudents() {

        String sql = "SELECT * FROM students WHERE status = 'ACTIVE'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- STUDENT RECORDS ---");

            while (rs.next()) {
                Student student = mapStudent(rs);

                System.out.println(
                        student.getStudentId() + " | "
                                + student.getStudentNumber() + " | "
                                + student.getFullName() + " | "
                                + student.getCourse() + " | "
                                + student.getYearLevel()
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchStudent(String keyword) {

        String sql = """
                SELECT * FROM students
                WHERE student_number LIKE ?
                OR first_name LIKE ?
                OR last_name LIKE ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String search = "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student s = mapStudent(rs);
                System.out.println(
                        s.getStudentId() + " | "
                                + s.getStudentNumber() + " | "
                                + s.getFullName()
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean updateStudent(int id, String course,
                                 String yearLevel, String section,
                                 String email) {

        String sql = """
                UPDATE students
                SET course = ?, year_level = ?,
                    section = ?, email = ?
                WHERE student_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course);
            ps.setString(2, yearLevel);
            ps.setString(3, section);
            ps.setString(4, email);
            ps.setInt(5, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deactivateStudent(int id) {

        String sql = """
                UPDATE students SET status = 'INACTIVE'
                WHERE student_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private Student mapStudent(ResultSet rs) throws SQLException {

        return new Student(
                rs.getInt("student_id"),
                rs.getInt("user_id"),
                rs.getString("student_number"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("course"),
                rs.getString("year_level"),
                rs.getString("section"),
                rs.getString("email"),
                rs.getString("status")
        );
    }
}