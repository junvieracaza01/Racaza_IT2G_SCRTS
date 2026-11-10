
package com.scc.ui;

import com.scc.dao.UserDao;
import com.scc.dao.StudentDao;
import com.scc.dao.ActivityLogDao;
import com.scc.model.User;

import java.util.Scanner;

public class Authentication {

    private static final Scanner sc = new Scanner(System.in);

    private static final UserDao userDao = new UserDao();
    private static final StudentDao studentDao = new StudentDao();
    private static final ActivityLogDao logDao = new ActivityLogDao();

    // FIX: Shared scanner method
    public static Scanner getScanner() {
        return sc;
    }

    public static void start() {

        while (true) {

            System.out.println("\n======================================");
            System.out.println(" STUDENT CLEARANCE TRACKING SYSTEM");
            System.out.println("======================================");
            System.out.println("[1] Login");
            System.out.println("[2] Register Student");
            System.out.println("[3] Exit");
            System.out.print("Choose option: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    login();
                    break;

                case "2":
                    registerStudent();
                    break;

                case "3":
                    System.out.println("Thank you for using the system.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void login() {

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = userDao.login(username, password);

        if (user == null) {
            System.out.println("Invalid credentials or inactive account.");
            return;
        }

        System.out.println("\nWelcome, " + user.getFullName() + "!");

        logDao.recordActivity(
                user.getUserId(),
                "LOGIN",
                "User logged into the system."
        );

        switch (user.getRole().toUpperCase()) {

            case "ADMIN":
            case "ADMINISTRATOR":
                AdminMenu.show(user);
                break;

            case "STAFF":
            case "DEPARTMENT STAFF":
            case "CLEARANCE STAFF":
                DepartmentStaffMenu.show(user);
                break;

            case "REGISTRAR":
            case "RECORDS OFFICER":
                RegistrarMenu.show(user);
                break;

            case "STUDENT":
                StudentMenu.show(user);
                break;

            default:
                System.out.println("Unrecognized user role.");
        }

        logDao.recordActivity(
                user.getUserId(),
                "LOGOUT",
                "User logged out of the system."
        );

        System.out.println("You have logged out.");
    }

    private static void registerStudent() {

        System.out.println("\n--- STUDENT REGISTRATION ---");

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Full Name: ");
        String fullName = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        boolean registered = userDao.registerUser(
                username,
                password,
                fullName,
                "STUDENT",
                email
        );

        if (!registered) {
            System.out.println("Registration failed. Username may already exist.");
            return;
        }

        User user = userDao.login(username, password);

        if (user == null) {
            System.out.println("User created, but student registration needs attention.");
            return;
        }

        System.out.print("Student Number: ");
        String studentNumber = sc.nextLine();

        System.out.print("First Name: ");
        String firstName = sc.nextLine();

        System.out.print("Last Name: ");
        String lastName = sc.nextLine();

        System.out.print("Course: ");
        String course = sc.nextLine();

        System.out.print("Year Level: ");
        String yearLevel = sc.nextLine();

        System.out.print("Section: ");
        String section = sc.nextLine();

        boolean added = studentDao.addStudent(
                user.getUserId(),
                studentNumber,
                firstName,
                lastName,
                course,
                yearLevel,
                section,
                email
        );

        if (added) {

            System.out.println("Student registered successfully.");

            logDao.recordActivity(
                    user.getUserId(),
                    "STUDENT_REGISTRATION",
                    "New student account and student record created."
            );

        } else {
            System.out.println("Student record creation failed.");
        }
    }
}
