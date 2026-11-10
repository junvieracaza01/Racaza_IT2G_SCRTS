package com.scc.dao;

import com.scc.model.ClearanceProgress;
import java.sql.*;

public class ClearanceRecordDao {

    public boolean assignRequirement(int studentId,
                                     int requirementId,
                                     int updatedBy) {

        String sql = """
                INSERT OR IGNORE INTO clearance_records
                (student_id, requirement_id, status, updated_by)
                VALUES (?, ?, 'Pending', ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, requirementId);
            ps.setInt(3, updatedBy);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateStatus(int clearanceId, String status,
                                String remarks, int updatedBy) {

        if (!status.equals("Pending")
                && !status.equals("Completed")
                && !status.equals("For Action")) {
            System.out.println("Invalid clearance status.");
            return false;
        }

        String sql = """
                UPDATE clearance_records
                SET status = ?, remarks = ?,
                    updated_by = ?,
                    date_updated = CURRENT_TIMESTAMP
                WHERE clearance_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, remarks);
            ps.setInt(3, updatedBy);
            ps.setInt(4, clearanceId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void displayStudentClearance(int studentId) {

        String sql = """
                SELECT cr.clearance_id,
                       r.requirement_name,
                       r.department,
                       cr.status,
                       cr.remarks,
                       cr.date_updated
                FROM clearance_records cr
                JOIN requirements r
                ON cr.requirement_id = r.requirement_id
                WHERE cr.student_id = ?
                ORDER BY cr.clearance_id
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- STUDENT CLEARANCE DETAILS ---");

            while (rs.next()) {
                System.out.println(
                        "Clearance ID: " + rs.getInt("clearance_id")
                                + "\nRequirement: " + rs.getString("requirement_name")
                                + "\nDepartment: " + rs.getString("department")
                                + "\nStatus: " + rs.getString("status")
                                + "\nRemarks: " + rs.getString("remarks")
                                + "\nUpdated: " + rs.getString("date_updated")
                                + "\n-------------------------"
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ClearanceProgress getProgress(int studentId) {

        String sql = """
                SELECT s.first_name, s.last_name, s.student_number,
                       COUNT(cr.clearance_id) AS total,
                       SUM(CASE WHEN cr.status = 'Completed'
                           THEN 1 ELSE 0 END) AS completed,
                       SUM(CASE WHEN cr.status = 'Pending'
                           THEN 1 ELSE 0 END) AS pending,
                       SUM(CASE WHEN cr.status = 'For Action'
                           THEN 1 ELSE 0 END) AS for_action
                FROM students s
                LEFT JOIN clearance_records cr
                    ON s.student_id = cr.student_id
                WHERE s.student_id = ?
                GROUP BY s.student_id
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new ClearanceProgress(
                        rs.getString("first_name") + " "
                                + rs.getString("last_name"),
                        rs.getString("student_number"),
                        rs.getInt("total"),
                        rs.getInt("completed"),
                        rs.getInt("pending"),
                        rs.getInt("for_action")
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void displayProgressReport() {

        String sql = """
                SELECT s.student_id
                FROM students s
                WHERE s.status = 'ACTIVE'
                ORDER BY s.student_id
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n========== STUDENT CLEARANCE PROGRESS REPORT ==========");

            while (rs.next()) {

                ClearanceProgress p = getProgress(
                        rs.getInt("student_id")
                );

                if (p != null) {
                    System.out.println("\nStudent: " + p.getStudentName());
                    System.out.println("Student ID: " + p.getStudentNumber());
                    System.out.println("Total Requirements: "
                            + p.getTotalRequirements());
                    System.out.println("Completed: "
                            + p.getCompletedRequirements());
                    System.out.println("Pending: "
                            + p.getPendingRequirements());
                    System.out.println("For Action: "
                            + p.getForActionRequirements());
                    System.out.printf("Completion: %.2f%%%n",
                            p.getCompletionPercentage());
                    System.out.println("Overall Status: "
                            + p.getOverallStatus());
                    System.out.println("---------------------------------------");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}