package com.scc.ui;

import com.scc.dao.StudentDao;
import com.scc.dao.ClearanceRecordDao;
import com.scc.model.Student;
import com.scc.model.User;
import com.scc.model.ClearanceProgress;

import java.util.Scanner;

public class StudentMenu {

    public static void show(User user) {

        Scanner sc = new Scanner(System.in);

        StudentDao studentDao = new StudentDao();
        ClearanceRecordDao clearanceDao = new ClearanceRecordDao();

        Student student = studentDao.findByUserId(user.getUserId());

        if (student == null) {
            System.out.println("Student record not found.");
            return;
        }

        while (true) {

            System.out.println("\n========== STUDENT MENU ==========");
            System.out.println("[1] View My Profile");
            System.out.println("[2] View Clearance Requirements");
            System.out.println("[3] View Clearance Progress");
            System.out.println("[4] Logout");

            System.out.print("Choose option: ");
            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    System.out.println("\n--- MY PROFILE ---");
                    System.out.println("Student Number: "
                            + student.getStudentNumber());
                    System.out.println("Name: "
                            + student.getFullName());
                    System.out.println("Course: "
                            + student.getCourse());
                    System.out.println("Year: "
                            + student.getYearLevel());
                    System.out.println("Section: "
                            + student.getSection());
                    System.out.println("Email: "
                            + student.getEmail());
                    break;

                case "2":
                    clearanceDao.displayStudentClearance(
                            student.getStudentId()
                    );
                    break;

                case "3":

                    ClearanceProgress progress =
                            clearanceDao.getProgress(student.getStudentId());

                    if (progress != null) {
                        System.out.println("\n--- CLEARANCE PROGRESS ---");
                        System.out.println("Total Requirements: "
                                + progress.getTotalRequirements());
                        System.out.println("Completed: "
                                + progress.getCompletedRequirements());
                        System.out.println("Pending: "
                                + progress.getPendingRequirements());
                        System.out.println("For Action: "
                                + progress.getForActionRequirements());

                        System.out.printf("Completion: %.2f%%%n",
                                progress.getCompletionPercentage());

                        System.out.println("Overall Status: "
                                + progress.getOverallStatus());
                    }
                    break;

                case "4":
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}