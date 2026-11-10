package com.scc.ui;

import com.scc.dao.StudentDao;
import com.scc.dao.RequirementDao;
import com.scc.dao.ClearanceRecordDao;
import com.scc.dao.ActivityLogDao;
import com.scc.model.User;

import java.util.Scanner;

public class RegistrarMenu {

    public static void show(User user) {

        Scanner sc = new Scanner(System.in);

        StudentDao studentDao = new StudentDao();
        RequirementDao requirementDao = new RequirementDao();
        ClearanceRecordDao clearanceDao = new ClearanceRecordDao();
        ActivityLogDao logDao = new ActivityLogDao();

        while (true) {

            System.out.println("\n========== REGISTRAR MENU ==========");
            System.out.println("[1] View Students");
            System.out.println("[2] Search Student");
            System.out.println("[3] Update Student Information");
            System.out.println("[4] Assign Requirement to Student");
            System.out.println("[5] View Clearance Progress Report");
            System.out.println("[6] View Student Clearance");
            System.out.println("[7] Logout");

            System.out.print("Choose option: ");
            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    studentDao.displayStudents();
                    break;

                case "2":
                    System.out.print("Enter Student Name or Number: ");
                    studentDao.searchStudent(sc.nextLine());
                    break;

                case "3":

                    System.out.print("Student ID: ");
                    int id = Integer.parseInt(sc.nextLine());

                    System.out.print("Course: ");
                    String course = sc.nextLine();

                    System.out.print("Year Level: ");
                    String year = sc.nextLine();

                    System.out.print("Section: ");
                    String section = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    if (studentDao.updateStudent(
                            id, course, year, section, email)) {

                        System.out.println("Student updated successfully.");

                        logDao.recordActivity(
                                user.getUserId(),
                                "STUDENT_UPDATE",
                                "Updated student ID: " + id
                        );

                    } else {
                        System.out.println("Student update failed.");
                    }
                    break;

                case "4":

                    System.out.print("Student ID: ");
                    int studentId = Integer.parseInt(sc.nextLine());

                    requirementDao.displayRequirements();

                    System.out.print("Requirement ID: ");
                    int requirementId = Integer.parseInt(sc.nextLine());

                    if (clearanceDao.assignRequirement(
                            studentId,
                            requirementId,
                            user.getUserId())) {

                        System.out.println("Requirement assigned successfully.");

                        logDao.recordActivity(
                                user.getUserId(),
                                "REQUIREMENT_ASSIGNMENT",
                                "Assigned requirement " + requirementId
                                        + " to student " + studentId
                        );

                    } else {
                        System.out.println(
                                "Assignment failed or record already exists."
                        );
                    }
                    break;

                case "5":
                    clearanceDao.displayProgressReport();
                    break;

                case "6":

                    System.out.print("Student ID: ");
                    int searchId = Integer.parseInt(sc.nextLine());

                    clearanceDao.displayStudentClearance(searchId);
                    break;

                case "7":
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}