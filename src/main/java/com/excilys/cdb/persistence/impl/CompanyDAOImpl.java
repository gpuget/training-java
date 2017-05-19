package com.excilys.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.UnauthorizedValueDAOException;
import com.excilys.cdb.mapper.row.CompanyRowMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;

@Repository("companyDao")
public class CompanyDAOImpl implements CompanyDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOImpl.class);

    private static final String FIND_ALL = "SELECT id, name FROM company";
    private static final String FIND_QUERY = "SELECT id, name FROM company WHERE id = ?";
    private static final String CREATE_QUERY = "INSERT INTO company (name) VALUES (?)";
    private static final String DELETE_QUERY = "DELETE FROM company WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Initialization.
     */
    @PostConstruct
    public void init() {
        LOGGER.info("Initialization CompanyDAO...");
        LOGGER.debug("Initialization JdbcTemplate");
    }

    @Override
    public Company create(Company company) {
        LOGGER.info("Create Company : " + company);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con)
                        throws SQLException {
                    PreparedStatement statement = con.prepareStatement(CREATE_QUERY);
                    statement.setString(1, company.getName());
                    LOGGER.debug("Query : " + statement);

                    return statement;
                }
            }, keyHolder);

            long id = (long) keyHolder.getKey();
            LOGGER.debug("Generated id : " + id);
            company.setId(id);
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to correctly create the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return company;
    }

    @Override
    public void delete(long id) {
        LOGGER.info("Delete company by id : " + id);
        String message = "Error : DAO has not been able to correctly delete the entity.";

        if (id > 0) {
            try {
                LOGGER.debug("Query : " + DELETE_QUERY);
                jdbcTemplate.update(DELETE_QUERY, id);
            } catch (DataAccessException e) {
                LOGGER.error(message);
                throw new DAOException(message, e);
            }
        } else {
            LOGGER.error(message);
            throw new UnauthorizedValueDAOException(message);
        }
    }

    @Override
    public List<Company> findAll() {
        LOGGER.info("Find all companies.");
        try {
            LOGGER.debug("Query : " + FIND_ALL);
            return jdbcTemplate.query(FIND_ALL, new CompanyRowMapper());
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to find the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public Company findById(long id) {
        LOGGER.info("Find company by id : " + id);
        String message = "Error : DAO has not been able to find the entity.";

        if (id > 0) {
            try {
                LOGGER.debug("Query : " + FIND_QUERY);
                return jdbcTemplate.query(FIND_QUERY, new CompanyRowMapper(), id).get(0);
            } catch (DataAccessException e) {
                LOGGER.error(message);
                throw new DAOException(message, e);
            }
        } else {
            LOGGER.error(message);
            throw new UnauthorizedValueDAOException(message);
        }
    }

    /**
     * Sets the jdbc template.
     * 
     * @param jdbcTemplate jdbcTemplate to set
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
