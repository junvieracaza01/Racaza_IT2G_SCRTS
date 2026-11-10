package com.scc.dao;

import com.scc.model.Requirement;
import java.sql.*;

public class RequirementDao {

    public boolean addRequirement(String name, String description,
                                  String department) {

        String sql = """
                INSERT INTO requirements
                (requirement_name, description, department)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, department);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void displayRequirements() {

        String sql = "SELECT * FROM requirements WHERE is_active = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- CLEARANCE REQUIREMENTS ---");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("requirement_id") + " | "
                                + rs.getString("requirement_name") + " | "
                                + rs.getString("department") + " | "
                                + rs.getString("description")
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Requirement findRequirement(int id) {

        String sql = """
                SELECT * FROM requirements
                WHERE requirement_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Requirement(
                        rs.getInt("requirement_id"),
                        rs.getString("requirement_name"),
                        rs.getString("description"),
                        rs.getString("department"),
                        rs.getInt("is_active") == 1
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean updateRequirement(int id, String name,
                                     String description,
                                     String department) {

        String sql = """
                UPDATE requirements
                SET requirement_name = ?,
                    description = ?, department = ?
                WHERE requirement_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, department);
            ps.setInt(4, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deactivateRequirement(int id) {

        String sql = """
                UPDATE requirements SET is_active = 0
                WHERE requirement_id = ?
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
}