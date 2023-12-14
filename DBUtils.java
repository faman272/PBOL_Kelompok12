package app;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Opens connection to database.
 */
public class DBUtils {

    private static String url = "jdbc:mysql://localhost:3306/";
    private static String username = "root";
    private static String password = "";

    public static void createDatabase() throws Exception {
        // creates 'ysoft' database when program launchs
        String sql = "create database if not exists sdm character set utf8";
        Connection con = DriverManager.getConnection(url, username, password);
        PreparedStatement statement = con.prepareStatement(sql);
        statement.execute();
        System.out.println("Database checked.");
    }

    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
