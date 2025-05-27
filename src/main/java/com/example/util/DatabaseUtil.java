package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseUtil {
    private static final String PROPERTIES_FILE = "database.properties";
    private static String DB_URL;
    private static Connection connection;

    static {
        try {
            // Загрузка конфигурации из файла
            Properties properties = new Properties();
            InputStream inputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);

            if (inputStream != null) {
                properties.load(inputStream);
                DB_URL = properties.getProperty("db.url");
                Class.forName(properties.getProperty("db.driver"));
            } else {
                throw new RuntimeException("Database configuration file not found: " + PROPERTIES_FILE);
            }

            connection = DriverManager.getConnection(DB_URL);
            initializeDatabase();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private static void initializeDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            executeSqlFile(stmt, "sql/init_db.sql");
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Метод для выполнения SQL из файла (опционально)
    private static void executeSqlFile(Statement stmt, String filePath) {
        try (InputStream is = DatabaseUtil.class.getClassLoader().getResourceAsStream(filePath)) {
            if (is != null) {
                String sql = new String(is.readAllBytes());
                String[] commands = sql.split(";");
                for (String command : commands) {
                    if (!command.trim().isEmpty()) {
                        stmt.execute(command);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}