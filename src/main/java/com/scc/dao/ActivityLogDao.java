package com.scc.dao;

import java.sql.*;

public class ActivityLogDao {

    public void recordActivity(int userId, String action,
                               String description) {

        String sql = """
                INSERT INTO activity_logs
                (user_id, action, description)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (userId > 0) {
                ps.setInt(1, userId);
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            ps.setString(2, action);
            ps.setString(3, description);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Activity log error: " + e.getMessage());
        }
    }

    public void displayLogs() {

        String sql = """
                SELECT a.log_id, u.username, a.action,
                       a.description, a.date_time
                FROM activity_logs a
                LEFT JOIN users u ON a.user_id = u.user_id
                ORDER BY a.log_id DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n========== SYSTEM ACTIVITY LOGS ==========");

            while (rs.next()) {
                System.out.println(
                        "Log ID: " + rs.getInt("log_id")
                                + "\nUser: " + rs.getString("username")
                                + "\nAction: " + rs.getString("action")
                                + "\nDescription: " + rs.getString("description")
                                + "\nDate: " + rs.getString("date_time")
                                + "\n----------------------------------"
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}