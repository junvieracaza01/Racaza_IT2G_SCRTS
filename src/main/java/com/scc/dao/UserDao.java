package com.scc.dao;

import com.scc.model.User;
import java.sql.*;

public class UserDao {
    public void ensureDefaultAdmin() {

        String sql = """
            SELECT COUNT(*) FROM users
            WHERE role = 'ADMIN'
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next() && rs.getInt(1) == 0) {

                registerUser(
                        "admin",
                        "admin123",
                        "System Administrator",
                        "ADMIN",
                        "admin@gmail.com"
                );

                System.out.println("Default administrator created.");
            }

        } catch (SQLException e) {
            System.out.println("Admin setup error: " + e.getMessage());
        }
    }

    public boolean registerUser(String username, String password,
                                String fullName, String role,
                                String email) {

        String sql = """
                INSERT INTO users
                (username, password, full_name, role, email)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, SecurityUtil.hashPassword(password));
            ps.setString(3, fullName);
            ps.setString(4, role);
            ps.setString(5, email);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    public User login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()
                    && "ACTIVE".equals(rs.getString("account_status"))
                    && SecurityUtil.verifyPassword(
                    password, rs.getString("password"))) {

                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("account_status")
                );
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }

        return null;
    }

    public User findUser(int userId) {

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("account_status")
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void displayUsers() {

        String sql = "SELECT user_id, username, full_name, role, email, account_status FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- USER ACCOUNTS ---");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("user_id") + " | "
                                + rs.getString("username") + " | "
                                + rs.getString("full_name") + " | "
                                + rs.getString("role") + " | "
                                + rs.getString("account_status")
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean updateUser(int id, String fullName,
                              String email, String role) {

        String sql = """
                UPDATE users
                SET full_name = ?, email = ?, role = ?
                WHERE user_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, role);
            ps.setInt(4, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deactivateUser(int id) {

        String sql = """
                UPDATE users SET account_status = 'INACTIVE'
                WHERE user_id = ?
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