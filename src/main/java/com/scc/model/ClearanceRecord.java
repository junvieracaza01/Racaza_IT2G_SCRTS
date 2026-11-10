package com.scc.model;

public class ClearanceRecord {

    private int clearanceId;
    private int studentId;
    private int requirementId;
    private String status;
    private String remarks;
    private int updatedBy;
    private String dateUpdated;

    public ClearanceRecord(int clearanceId, int studentId,
                           int requirementId, String status,
                           String remarks, int updatedBy,
                           String dateUpdated) {

        this.clearanceId = clearanceId;
        this.studentId = studentId;
        this.requirementId = requirementId;
        this.status = status;
        this.remarks = remarks;
        this.updatedBy = updatedBy;
        this.dateUpdated = dateUpdated;
    }

    public int getClearanceId() { return clearanceId; }
    public int getStudentId() { return studentId; }
    public int getRequirementId() { return requirementId; }
    public String getStatus() { return status; }
    public String getRemarks() { return remarks; }
    public int getUpdatedBy() { return updatedBy; }
    public String getDateUpdated() { return dateUpdated; }
}