package com.excilys.cdb.persistences;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum Connector {
    INSTANCE;

    private Connection connection;
    private Properties properties;

    /**
     * Seek DB properties and loads it.
     */
    Connector() {
        try {
            properties = new Properties();
            properties.load(new FileInputStream(
                    "src/main/resources/local.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects to the DB corresponding to the properties.
     */
    private void connect() {
        try {
            Class.forName(properties.getProperty("db.driver"));
            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects from the DB.
     */
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the connection to the DB, reconnected if disconnected.
     * @return connection to the db
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Gets the state of the connection.
     * @return state of the connection
     */
    public boolean isDisconnected() {
        try {
            return (connection == null || connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}