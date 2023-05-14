/*
Central Queensland University
COIT12200 - Software Design & Development (2023 Term 1)
Campus: External
Assignment 2 - Complaints Management System
Student ID: 12184305
Student Name: Daniel Barros
*/
package CMS.Util;

import java.sql.*;

/**
 * Class for handling database connection for the Complaints Management System.
 */
public class DatabaseHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/complaintsdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password@Cqu";

    /**
     * Establishes a connection to the database.
     * @return a Connection object
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }
}
