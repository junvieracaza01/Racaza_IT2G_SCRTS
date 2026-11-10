package com.scc.ui;

import com.scc.dao.UserDao;
import com.scc.dao.StudentDao;
import com.scc.dao.RequirementDao;
import com.scc.dao.ClearanceRecordDao;
import com.scc.dao.ActivityLogDao;
import com.scc.model.User;

import java.util.Scanner;

public class AdminMenu {

    public static void show(User user) {

        Scanner sc = new Scanner(System.in);

        UserDao userDao = new UserDao();
        StudentDao studentDao = new StudentDao();
        RequirementDao requirementDao = new RequirementDao();
        ClearanceRecordDao clearanceDao = new ClearanceRecordDao();
        ActivityLogDao logDao = new ActivityLogDao();

        while (true) {

            System.out.println("\n========== ADMINISTRATOR MENU ==========");
            System.out.println("[1] View All Users");
            System.out.println("[2] Register Staff or Registrar Account");
            System.out.println("[3] Update User Account");
            System.out.println("[4] Deactivate User Account");
            System.out.println("[5] View Students");
            System.out.println("[6] Add Clearance Requirement");
            System.out.println("[7] View Requirements");
            System.out.println("[8] Edit Requirement");
            System.out.println("[9] Deactivate Requirement");
            System.out.println("[10] Clearance Progress Report");
            System.out.println("[11] View Activity Logs");
            System.out.println("[12] Logout");

            System.out.print("Choose option: ");
            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    userDao.displayUsers();
                    break;

                case "2":

                    System.out.print("Username: ");
                    String username = sc.nextLine();

                    System.out.print("Password: ");
                    String password = sc.nextLine();

                    System.out.print("Full Name: ");
                    String name = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.println("1. Department Staff");
                    System.out.println("2. Registrar");

                    System.out.print("Choose Role: ");
                    String roleChoice = sc.nextLine();

                    String role;

                    if (roleChoice.equals("1")) {
                        role = "STAFF";
                    } else if (roleChoice.equals("2")) {
                        role = "REGISTRAR";
                    } else {
                        System.out.println("Invalid role.");
                        break;
                    }

                    if (userDao.registerUser(
                            username, password, name, role, email)) {

                        System.out.println("Account created successfully.");

                        logDao.recordActivity(
                                user.getUserId(),
                                "ACCOUNT_CREATION",
                                "Created account for " + username
                        );

                    } else {
                        System.out.println("Account creation failed.");
                    }
                    break;

                case "3":

                    System.out.print("User ID: ");
                    int userId = Integer.parseInt(sc.nextLine());

                    System.out.print("New Full Name: ");
                    String fullName = sc.nextLine();

                    System.out.print("New Email: ");
                    String newEmail = sc.nextLine();

                    System.out.print("New Role (ADMIN, STAFF, REGISTRAR, STUDENT): ");
                    String newRole = sc.nextLine().toUpperCase();

                    if (userDao.updateUser(
                            userId, fullName, newEmail, newRole)) {

                        System.out.println("User updated successfully.");

                        logDao.recordActivity(
                                user.getUserId(),
                                "ACCOUNT_UPDATE",
                                "Updated user ID: " + userId
                        );

                    } else {
                        System.out.println("User update failed.");
                    }
                    break;

                case "4":

                    System.out.print("Enter User ID to deactivate: ");
                    int deactivateId = Integer.parseInt(sc.nextLine());

                    if (deactivateId == user.getUserId()) {
                        System.out.println("You cannot deactivate your own account.");
                        break;
                    }

                    if (userDao.deactivateUser(deactivateId)) {

                        System.out.println("Account deactivated.");

                        logDao.recordActivity(
                                user.getUserId(),
                                "ACCOUNT_DEACTIVATION",
                                "Deactivated user ID: " + deactivateId
                        );

                    } else {
                        System.out.println("Deactivation failed.");
                    }
                    break;

                case "5":
                    studentDao.displayStudents();
                    break;

                case "6":

                    System.out.print("Requirement Name: ");
                    String reqName = sc.nextLine();

                    System.out.print("Description: ");
                    String description = sc.nextLine();

                    System.out.print("Department: ");
                    String department = sc.nextLine();

                    if (requirementDao.addRequirement(
                            reqName, description, department)) {

                        System.out.println("Requirement added successfully.");

                        logDao.recordActivity(
                                user.getUserId(),
                                "REQUIREMENT_CREATION",
                                "Added requirement: " + reqName
                        );

                    } else {
                        System.out.println("Failed to add requirement.");
                    }
                    break;

                case "7":
                    requirementDao.displayRequirements();
                    break;

                case "8":

                    requirementDao.displayRequirements();

                    System.out.print("Requirement ID: ");
                    int reqId = Integer.parseInt(sc.nextLine());

                    System.out.print("New Requirement Name: ");
                    String newName = sc.nextLine();

                    System.out.print("New Description: ");
                    String newDescription = sc.nextLine();

                    System.out.print("New Department: ");
                    String newDepartment = sc.nextLine();

                    if (requirementDao.updateRequirement(
                            reqId, newName,
                            newDescription, newDepartment)) {

                        System.out.println("Requirement updated.");

                        logDao.recordActivity(
                                user.getUserId(),
                                "REQUIREMENT_UPDATE",
                                "Updated requirement ID: " + reqId
                        );

                    } else {
                        System.out.println("Update failed.");
                    }
                    break;

                case "9":

                    requirementDao.displayRequirements();

                    System.out.print("Requirement ID: ");
                    int removeId = Integer.parseInt(sc.nextLine());

                    if (requirementDao.deactivateRequirement(removeId)) {

                        System.out.println("Requirement deactivated.");

                        logDao.recordActivity(
                                user.getUserId(),
                                "REQUIREMENT_DEACTIVATION",
                                "Deactivated requirement ID: " + removeId
                        );

                    } else {
                        System.out.println("Deactivation failed.");
                    }
                    break;

                case "10":
                    clearanceDao.displayProgressReport();
                    break;

                case "11":
                    logDao.displayLogs();
                    break;

                case "12":
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}