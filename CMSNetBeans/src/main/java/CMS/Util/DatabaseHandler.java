package CMS.Util;

import java.sql.*;

/**
 *
 * @author Daniel Barros
 */
public class DatabaseHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/complaintsdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password@Cqu";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }
}
