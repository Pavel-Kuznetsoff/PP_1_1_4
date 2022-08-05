package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String urlString = "jdbc:mysql://localhost:3306/mysql";
    private static String userName = "admin";
    private static String password = "1234";
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(urlString, userName, password);
            } catch (SQLException ex) {
                System.out.println("Database connection error.");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
