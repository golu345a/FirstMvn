package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/ankit";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private DBUtil() {
    }

    public static Connection getConnection() throws  SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}