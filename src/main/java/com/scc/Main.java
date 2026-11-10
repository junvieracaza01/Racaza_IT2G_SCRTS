package com.scc;

import com.scc.dao.DatabaseInitializer;
import com.scc.dao.UserDao;
import com.scc.ui.Authentication;

public class Main {

    public static void main(String[] args) {

        // Initialize database tables
        DatabaseInitializer.initialize();

        // Create default administrator if none exists
        UserDao userDao = new UserDao();
        userDao.ensureDefaultAdmin();

        // Start authentication
        Authentication.start();
    }
}