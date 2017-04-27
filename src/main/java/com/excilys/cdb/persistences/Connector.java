package com.excilys.cdb.persistences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum Connector {
    INSTANCE;

    private HikariDataSource dataSource;
    private Properties properties;

    /**
     * Seek DB properties and loads it.
     */
    Connector() {
        try {
            properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("local.properties"));
            HikariConfig config = new HikariConfig();
            Class.forName(properties.getProperty("db.driver"));
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));
            config.addDataSourceProperty("cachePrepStmts", properties.getProperty("dataSource.cachePrepStmts"));
            config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty("dataSource.prepStmtCacheSize"));
            config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty("dataSource.prepStmtCacheSqlLimit"));
            
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new ConnectorException("Error : Properties cannot be loaded.", e);
        }
    }

    /**
     * Gets the connection to the DB, reconnected if disconnected.
     * @return connection to the db
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectorException("Error : Database connection has not been correctly done.", e);
        }
    }
}