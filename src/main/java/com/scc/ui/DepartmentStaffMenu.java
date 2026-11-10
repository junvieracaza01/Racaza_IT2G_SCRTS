package com.scc.ui;

import com.scc.dao.StudentDao;
import com.scc.dao.ClearanceRecordDao;
import com.scc.dao.RequirementDao;
import com.scc.dao.ActivityLogDao;
import com.scc.model.User;

import java.util.Scanner;

public class DepartmentStaffMenu {

    public static void show(User user) {

        Scanner sc = Authentication.getScanner();

        StudentDao studentDao = new StudentDao();
        ClearanceRecordDao clearanceDao = new ClearanceRecordDao();
        RequirementDao requirementDao = new RequirementDao();
        ActivityLogDao logDao = new ActivityLogDao();

        while (true) {

            System.out.println("\n======= DEPARTMENT STAFF MENU =======");
            System.out.println("[1] View Students");
            System.out.println("[2] View Requirements");
            System.out.println("[3] View Student Clearance");
            System.out.println("[4] Update Clearance Status");
            System.out.println("[5] View Activity Logs");
            System.out.println("[6] Logout");

            System.out.print("Choose option: ");
            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    studentDao.displayStudents();
                    break;

                case "2":
                    requirementDao.displayRequirements();
                    break;

                case "3":
                    System.out.print("Enter Student ID: ");
                    int studentId = Integer.parseInt(sc.nextLine());

                    clearanceDao.displayStudentClearance(studentId);
                    break;

                case "4":

                    System.out.print("Enter Clearance ID: ");
                    int clearanceId = Integer.parseInt(sc.nextLine());

                    System.out.println("1. Pending");
                    System.out.println("2. Completed");
                    System.out.println("3. For Action");

                    System.out.print("Select Status: ");
                    String statusChoice = sc.nextLine();

                    // FIX: Initialize status before the switch
                    String status = "";

                    switch (statusChoice) {

                        case "1":
                            status = "Pending";
                            break;

                        case "2":
                            status = "Completed";
                            break;

                        case "3":
                            status = "For Action";
                            break;

                        default:
                            System.out.println("Invalid status.");
                            break;
                    }

                    // Check if status is valid
                    if (status.isEmpty()) {
                        break;
                    }

                    System.out.print("Enter Remarks: ");
                    String remarks = sc.nextLine();

                    if (clearanceDao.updateStatus(
                            clearanceId,
                            status,
                            remarks,
                            user.getUserId())) {

                        System.out.println("Clearance updated successfully.");

                        logDao.recordActivity(
                                user.getUserId(),
                                "CLEARANCE_UPDATE",
                                "Updated clearance ID: " + clearanceId
                        );

                    } else {

                        System.out.println("Update failed.");

                    }

                    break;

                case "5":
                    logDao.displayLogs();
                    break;

                case "6":
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}