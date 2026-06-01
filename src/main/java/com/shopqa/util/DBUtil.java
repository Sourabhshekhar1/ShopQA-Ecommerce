package com.shopqa.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new ExceptionInInitializerError("db.properties not found on classpath");
            }
            PROPERTIES.load(input);
            Class.forName(PROPERTIES.getProperty("db.driver"));
        } catch (IOException | ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DBUtil() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            PROPERTIES.getProperty("db.url"),
            PROPERTIES.getProperty("db.username"),
            PROPERTIES.getProperty("db.password")
        );
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }
}

