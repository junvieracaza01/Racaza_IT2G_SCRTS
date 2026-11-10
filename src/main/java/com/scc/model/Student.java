package com.scc.model;

public class Student {

    private int studentId;
    private int userId;
    private String studentNumber;
    private String firstName;
    private String lastName;
    private String course;
    private String yearLevel;
    private String section;
    private String email;
    private String status;

    public Student(int studentId, int userId,
                   String studentNumber, String firstName,
                   String lastName, String course,
                   String yearLevel, String section,
                   String email, String status) {

        this.studentId = studentId;
        this.userId = userId;
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.yearLevel = yearLevel;
        this.section = section;
        this.email = email;
        this.status = status;
    }

    public int getStudentId() { return studentId; }
    public int getUserId() { return userId; }
    public String getStudentNumber() { return studentNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCourse() { return course; }
    public String getYearLevel() { return yearLevel; }
    public String getSection() { return section; }
    public String getEmail() { return email; }
    public String getStatus() { return status; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}