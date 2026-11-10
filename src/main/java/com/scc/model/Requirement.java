package com.scc.model;

public class Requirement {

    private int requirementId;
    private String requirementName;
    private String description;
    private String department;
    private boolean active;

    public Requirement(int requirementId, String requirementName,
                       String description, String department,
                       boolean active) {

        this.requirementId = requirementId;
        this.requirementName = requirementName;
        this.description = description;
        this.department = department;
        this.active = active;
    }

    public int getRequirementId() { return requirementId; }
    public String getRequirementName() { return requirementName; }
    public String getDescription() { return description; }
    public String getDepartment() { return department; }
    public boolean isActive() { return active; }
}