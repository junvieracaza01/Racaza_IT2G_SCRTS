package com.scc.model;

public class User {

    private int userId;
    private String username;
    private String password;
    private String fullName;
    private String role;
    private String email;
    private String accountStatus;

    public User(int userId, String username, String password,
                String fullName, String role, String email,
                String accountStatus) {

        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.email = email;
        this.accountStatus = accountStatus;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountStatus() {
        return accountStatus;
    }
}