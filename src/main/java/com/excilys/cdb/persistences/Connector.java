package com.excilys.cdb.persistences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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
            properties.load(this.getClass().getClassLoader().getResourceAsStream("local.properties"));
        } catch (Exception e) {
            throw new ConnectorException("Error : Properties cannot be loaded.", e);
        }
    }

    /**
     * Connects to the DB corresponding to the properties.
     */
    private void connect() throws ClassNotFoundException, SQLException{
        Class.forName(properties.getProperty("db.driver"));
        connection = DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));
    }
    
    private void connectWithHikari() throws ClassNotFoundException, SQLException {
        HikariConfig config = new HikariConfig();
        Class.forName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));
        config.addDataSourceProperty("cachePrepStmts", properties.getProperty("dataSource.cachePrepStmts"));
        config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty("dataSource.prepStmtCacheSize"));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty("dataSource.prepStmtCacheSqlLimit"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("dataSource.maximalPoolSize")));
        
        HikariDataSource dataSource = new HikariDataSource(config); 
        connection = dataSource.getConnection();
        dataSource.close();
    }

    /**
     * Disconnects from the DB.
     */
    public void disconnect() {
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            throw new ConnectorException("Error : Database connection has not been correctly closed.", e);
        }
    }

    /**
     * Gets the connection to the DB, reconnected if disconnected.
     * @return connection to the db
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connectWithHikari();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ConnectorException("Error : Database connection cannot be done.", e);
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
            throw new ConnectorException("Error : Connector has encountered a problem during the connection check.", e);
        }
    }
}