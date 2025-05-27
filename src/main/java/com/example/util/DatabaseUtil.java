package com.example.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static final String DB_URL;
    private static final String DB_DRIVER;

    static {
        try {
            Properties props = new Properties();
            props.load(DatabaseUtil.class.getClassLoader()
                    .getResourceAsStream("application.properties"));

            DB_URL = props.getProperty("db.url")
                    .replace("${project.basedir}", System.getProperty("user.dir"));
            DB_DRIVER = props.getProperty("db.driver");

            // Проверяем существование файла БД, если нет - создаём
            File dbFile = new File(DB_URL.replace("jdbc:sqlite:", ""));
            if (!dbFile.exists()) {
                initializeDatabase();
            }

            Class.forName(DB_DRIVER);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static void initializeDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        // Здесь можно выполнить дополнительные инициализации при первом запуске
        conn.close();
    }
}