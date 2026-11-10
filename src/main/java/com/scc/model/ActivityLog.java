package com.scc.model;

public class ActivityLog {

    private int logId;
    private int userId;
    private String action;
    private String description;
    private String dateTime;

    public ActivityLog(int logId, int userId,
                       String action, String description,
                       String dateTime) {

        this.logId = logId;
        this.userId = userId;
        this.action = action;
        this.description = description;
        this.dateTime = dateTime;
    }

    public int getLogId() { return logId; }
    public int getUserId() { return userId; }
    public String getAction() { return action; }
    public String getDescription() { return description; }
    public String getDateTime() { return dateTime; }
}