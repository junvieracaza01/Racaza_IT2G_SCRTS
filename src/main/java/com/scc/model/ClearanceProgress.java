package com.scc.model;

public class ClearanceProgress {

    private String studentName;
    private String studentNumber;
    private int totalRequirements;
    private int completedRequirements;
    private int pendingRequirements;
    private int forActionRequirements;
    private double completionPercentage;
    private String overallStatus;

    public ClearanceProgress(String studentName, String studentNumber,
                             int totalRequirements,
                             int completedRequirements,
                             int pendingRequirements,
                             int forActionRequirements) {

        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.totalRequirements = totalRequirements;
        this.completedRequirements = completedRequirements;
        this.pendingRequirements = pendingRequirements;
        this.forActionRequirements = forActionRequirements;

        if (totalRequirements > 0) {
            completionPercentage =
                    (completedRequirements * 100.0) / totalRequirements;
        }

        if (totalRequirements == 0) {
            overallStatus = "No Requirements";
        } else if (completedRequirements == totalRequirements) {
            overallStatus = "Completed";
        } else if (forActionRequirements > 0) {
            overallStatus = "For Action";
        } else {
            overallStatus = "Pending";
        }
    }

    public String getStudentName() { return studentName; }
    public String getStudentNumber() { return studentNumber; }
    public int getTotalRequirements() { return totalRequirements; }
    public int getCompletedRequirements() { return completedRequirements; }
    public int getPendingRequirements() { return pendingRequirements; }
    public int getForActionRequirements() { return forActionRequirements; }
    public double getCompletionPercentage() { return completionPercentage; }
    public String getOverallStatus() { return overallStatus; }
}