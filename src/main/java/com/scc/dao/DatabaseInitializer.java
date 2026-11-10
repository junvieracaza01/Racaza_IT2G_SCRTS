package com.scc.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void initialize() {

        String[] tables = {

                """
            CREATE TABLE IF NOT EXISTS users (
                user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                full_name TEXT NOT NULL,
                role TEXT NOT NULL,
                email TEXT,
                account_status TEXT DEFAULT 'ACTIVE'
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS students (
                student_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER UNIQUE,
                student_number TEXT NOT NULL UNIQUE,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                course TEXT,
                year_level TEXT,
                section TEXT,
                email TEXT,
                status TEXT DEFAULT 'ACTIVE',
                FOREIGN KEY(user_id) REFERENCES users(user_id)
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS requirements (
                requirement_id INTEGER PRIMARY KEY AUTOINCREMENT,
                requirement_name TEXT NOT NULL,
                description TEXT,
                department TEXT,
                is_active INTEGER DEFAULT 1
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS clearance_records (
                clearance_id INTEGER PRIMARY KEY AUTOINCREMENT,
                student_id INTEGER NOT NULL,
                requirement_id INTEGER NOT NULL,
                status TEXT DEFAULT 'Pending'
                    CHECK(status IN ('Pending','Completed','For Action')),
                remarks TEXT,
                updated_by INTEGER,
                date_updated TEXT DEFAULT CURRENT_TIMESTAMP,
                UNIQUE(student_id, requirement_id),
                FOREIGN KEY(student_id) REFERENCES students(student_id),
                FOREIGN KEY(requirement_id) REFERENCES requirements(requirement_id),
                FOREIGN KEY(updated_by) REFERENCES users(user_id)
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS activity_logs (
                log_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                action TEXT NOT NULL,
                description TEXT,
                date_time TEXT DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY(user_id) REFERENCES users(user_id)
            )
            """
        };

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON");

            for (String sql : tables) {
                stmt.execute(sql);
            }

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.out.println("Database initialization error: "
                    + e.getMessage());
        }
    }
}