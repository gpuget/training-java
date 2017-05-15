package com.excilys.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.Connector;

@Repository("companyDao")
public class CompanyDAOImpl implements CompanyDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOImpl.class);

    private static final String FIND_ALL = "SELECT id, name FROM company";
    private static final String FIND_QUERY = "SELECT com.id, com.name " + "FROM company AS  com "
            + "WHERE com.id = ?";
    private static final String CREATE_QUERY = "INSERT INTO company (name) VALUES (?)";
    private static final String DELETE_QUERY = "DELETE FROM company WHERE company.id = ?";

    @Autowired
    private Connector connector;

    @Override
    public List<Company> findAll() {
        LOGGER.info("Find all companies.");
        ArrayList<Company> companies = new ArrayList<>();

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL);
                ResultSet resultSet = statement.executeQuery()) {
            LOGGER.debug("Query : " + statement);
            while (resultSet.next()) {
                companies.add(new Company.Builder().id(resultSet.getLong("id"))
                        .name(resultSet.getString("name")).build());
            }
            LOGGER.debug("Found companies : " + companies);
        } catch (SQLException e) {
            String message = "Error : DAO has not been able to find the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return companies;
    }

    @Override
    public Company findById(long id) {
        LOGGER.info("Find company by id : " + id);
        Company company = null;

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_QUERY)) {
            statement.setLong(1, id);
            LOGGER.debug("Query : " + statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.first()) {
                    company = mapResultSet(resultSet);
                    LOGGER.debug("Found company : " + company);
                }
            }
        } catch (SQLException e) {
            String message = "Error : DAO has not been able to find the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return company;
    }

    @Override
    public void delete(long id) {
        LOGGER.info("Delete company by id : " + id);
        try (Connection connection = connector.getConnection()) {
            delete(id, connection);
        } catch (SQLException e) {
            String message = "Error : DAO has not been able to correctly connect to the database.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public void delete(long id, Connection connection) {
        LOGGER.info("Delete company by id : " + id);
        LOGGER.debug("Connection used : " + connection);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            LOGGER.debug("Query : " + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            String message = "Error : DAO has not been able to correctly delete the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public Company create(Company company) {
        LOGGER.info("Create Company : " + company);
        String message = "Error : DAO has not been able to correctly create the entity.";

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, company.getName());
            LOGGER.debug("Query : " + statement);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys();) {
                if (rs.first()) {
                    long id = rs.getLong(1);
                    company.setId(id);
                    LOGGER.debug("Generated id : " + id);
                } else {
                    LOGGER.error(message);
                    throw new DAOException(message);
                }
            }

        } catch (SQLException e) {
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return company;
    }

    /**
     * Create a Company with a result set.
     *
     * @param rs provided result set
     * @return mapped company
     * @throws SQLException if result set is not validate
     */
    private Company mapResultSet(ResultSet rs) throws SQLException {
        LOGGER.info("Get a Company from result set");
        return new Company.Builder().id(rs.getLong("id")).name(rs.getString("name")).build();
    }

    /**
     * Sets the connector.
     *
     * @param connector connector to use
     */
    public void setConnector(Connector connector) {
        LOGGER.info("Set connector : " + connector);
        this.connector = connector;
    }
}
