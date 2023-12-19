package database;

import config.Config;

import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements AutoCloseable{

    private static Connection connection;
    static {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/console_bank_system",
                    Config.getDatabaseUser(),
                    Config.getDatabasePassword());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        System.out.println("CLOSING CONNECTION");
        if (connection != null && !connection.isClosed()) {
            System.out.println("CLOSING CONNECTION");
            connection.close();
        }
    }
}
