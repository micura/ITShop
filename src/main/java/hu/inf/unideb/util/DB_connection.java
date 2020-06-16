package hu.inf.unideb.util;

import java.sql.*;

public class DB_connection {
    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String URL = "jdbc:mysql://localhost:3306/itshop?serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "root";

    Connection connection = null;
    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}