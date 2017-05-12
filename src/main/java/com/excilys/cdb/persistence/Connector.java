package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.excilys.cdb.exception.DAOException;

public class Connector {
    private static final Logger LOGGER = LoggerFactory.getLogger(Connector.class);

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        LOGGER.trace("Set datasource : " + dataSource);
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        LOGGER.trace("Get connection");
        String message;
        
        try {
            if (dataSource != null) {
                return dataSource.getConnection();
            } else {
                message = "There is not datasource.";
                LOGGER.error(message);
                throw new DAOException(message);
            }
        } catch (SQLException e) {
            message = "The connection was not establish.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }
}