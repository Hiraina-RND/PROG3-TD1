package hei.td;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String URL;
    private final String USER;
    private final String PASSWORD;


    public DBConnection(String URL, String USER, String PASSWORD) {
        this.URL = URL;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }


    public Connection getDBConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(SQLException e) {
            throw new RuntimeException("Error connecting on PostgreSQL", e);
        }
    }
}
