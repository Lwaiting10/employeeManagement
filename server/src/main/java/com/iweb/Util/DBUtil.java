package com.iweb.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Liu Xiong
 * @date 25/11/2023 下午4:21
 */
public class DBUtil {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "a12345";
    private final static String URL = "jdbc:mysql://localhost:3306/employee_management_db?characterEncoding=utf8";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
