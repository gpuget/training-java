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

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.Connector;

public enum CompanyDAOImpl implements CompanyDAO {
	INSTANCE;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
    private static final String FIND_ALL = "SELECT id, name FROM company";
    private static final String FIND_QUERY = "SELECT com.id, com.name "
									            + "FROM company AS  com "
									            + "WHERE com.id = ?";
    private static final String CREATE_QUERY = "INSERT INTO company (name) VALUES (?)";
    private static final String DELETE_QUERY = "DELETE FROM company WHERE company.id = ?";

    @Override
    public List<Company> findAll() {
    	LOGGER.info("Find all companies.");
        ArrayList<Company> companiesList = new ArrayList<>();

        try (Connection connection = Connector.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL);
                ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                companiesList.add(new Company.Builder()
                                                .id(resultSet.getLong("id"))
                                                .name(resultSet.getString("name")).build());
            }
        } catch (SQLException e) {
        	String message = "Error : DAO has not been able to find the entity.";
        	LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return companiesList;
    }

    @Override
    public Company findById(long id) {
    	LOGGER.info("Find company by id : " + id);
        Company company = null;

        try (Connection connection = Connector.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_QUERY);) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery();) {
                if (rs.first()) {
                    company = loadCompany(rs);
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
    	try (Connection connection = Connector.INSTANCE.getConnection()) {
    		delete(id, connection);
    	} catch (SQLException e) {
        	String message = "Error : DAO has not been able to correctly connect to the database.";
        	LOGGER.error(message);
            throw new DAOException(message, e);
        }
	}
    
	public void delete(long id, Connection connection) {
    	LOGGER.info("Delete company by id : " + id);
    	LOGGER.debug("Connection used : " + connection);
    	try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
    		statement.setLong(1, id);
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
        try (Connection connection = Connector.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);) {
        	statement.setString(1, company.getName());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys();){
                if (rs.first()) {
                	company.setId(rs.getLong(1));
                } else {
                    throw new DAOException("Error : DAO has not been able to correctly create the entity.");
                }
            }
            
        } catch (SQLException e) {
        	String message = "Error : DAO has not been able to correctly create the entity.";
        	LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return company;
    }

	private Company loadCompany(ResultSet rs) throws SQLException {
        return new Company.Builder().id(rs.getLong("id"))
                                    .name(rs.getString("name")).build();
    }
}
