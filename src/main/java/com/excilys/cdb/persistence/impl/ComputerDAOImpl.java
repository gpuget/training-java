package com.excilys.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.mapper.row.ComputerRowMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.persistence.Connector;

@Repository("computerDao")
public class ComputerDAOImpl implements ComputerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAOImpl.class);

    private static final String FIND_QUERY = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id, "
            + "com.name AS company_name " + "FROM computer AS  cpu "
            + "LEFT JOIN company AS com ON cpu.company_id = com.id WHERE cpu.id = ?";
    private static final String FIND_ALL = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id, "
            + "com.name AS company_name " + "FROM computer AS  cpu "
            + "LEFT JOIN company as com ON cpu.company_id = com.id";
    private static final String CREATE_QUERY = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM computer WHERE computer.id = ?";
    private static final String UPDATE_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
    private static final String DELETE_IN = "DELETE FROM computer WHERE id IN";
    private static final String DELETE_FROM_COMPANY = "DELETE FROM computer WHERE company_id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(cpu.id) FROM computer AS cpu";
    private static final String BOUNDED_RESULT = " LIMIT ? OFFSET ?";
    private static final String LIKE_NAME = " WHERE cpu.name LIKE ?";

    @Autowired
    private Connector connector;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        LOGGER.info("Initialization ComputerDAO...");
        LOGGER.debug("Initialization JdbcTemplate");
        jdbcTemplate = new JdbcTemplate(connector.getDataSource());
    }

    @Override
    public Computer create(Computer computer) {
        LOGGER.info("Create computer : " + computer);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con)
                        throws SQLException {
                    PreparedStatement statement = con.prepareStatement(CREATE_QUERY);
                    setStatementValues(statement, computer);
                    LOGGER.debug("Query : " + statement);

                    return statement;
                }
            }, keyHolder);

            long id = (long) keyHolder.getKey();
            LOGGER.debug("Generated id : " + id);
            computer.setId(id);
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to correctly create the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return computer;
    }

    @Override
    public void delete(long id) {
        LOGGER.info("Delete computer by id : " + id);

        try {
            LOGGER.debug("Query : " + DELETE_QUERY);
            jdbcTemplate.update(DELETE_QUERY, id);
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to correctly delete the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public void delete(List<Long> idsList) {
        LOGGER.info("Delete all computer in : " + idsList);
        StringBuilder sqlQueryBuilder = new StringBuilder(DELETE_IN);

        sqlQueryBuilder.append('(');
        sqlQueryBuilder.append(idsList.get(0));
        for (int i = 1; i < idsList.size(); i++) {
            sqlQueryBuilder.append(", ").append(idsList.get(i));
        }
        sqlQueryBuilder.append(')');

        LOGGER.debug("Query : " + sqlQueryBuilder.toString());
        try {
            jdbcTemplate.update(sqlQueryBuilder.toString());
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to correctly delete entities.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public void deleteFromCompany(long companyId) {
        LOGGER.info("Delete computer from company : " + companyId);

        try {
            LOGGER.debug("Query : " + DELETE_FROM_COMPANY);
            jdbcTemplate.update(DELETE_FROM_COMPANY, companyId);
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to correctly delete the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public List<Computer> findAll() {
        LOGGER.info("Find all computers.");
        try {
            LOGGER.debug("Query : " + FIND_ALL);
            return jdbcTemplate.query(FIND_ALL, new ComputerRowMapper());
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to correctly find all entities.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public List<Computer> findAll(int limit, int offset) {
        LOGGER.info("Find all computers : " + limit + " " + offset);

        try {
            String sqlQuery = FIND_ALL + BOUNDED_RESULT;
            LOGGER.debug("Query : " + sqlQuery);
            return jdbcTemplate.query(sqlQuery, new Integer[] {limit, offset},
                    new ComputerRowMapper());
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to correctly find all entities.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public Computer findById(long id) {
        LOGGER.info("Find computer by id : " + id);

        try {
            LOGGER.debug("Query : " + FIND_QUERY);
            return jdbcTemplate.queryForObject(FIND_QUERY, new Object[] {id},
                    new ComputerRowMapper());
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to find the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public Computer update(Computer computer) {
        LOGGER.info("Update computer :" + computer);

        try {
            jdbcTemplate.update(UPDATE_QUERY, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    setStatementValues(ps, computer);
                    ps.setLong(5, computer.getId());
                    LOGGER.debug("Query : " + ps);
                }
            });
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to correctly update the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return computer;
    }

    @Override
    public int getCount() {
        LOGGER.info("Count computers");
        String message = "Error : DAO has not been able to correctly count the entities.";

        try {
            LOGGER.debug("Query : " + COUNT_QUERY);
            return jdbcTemplate.queryForObject(COUNT_QUERY, Integer.class);
        } catch (DataAccessException e) {
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public List<Computer> getFilteredByName(int limit, int offset, String name) {
        LOGGER.info("Find all computer with name : " + name);

        try {
            String sqlQuery = FIND_ALL + LIKE_NAME + BOUNDED_RESULT;
            LOGGER.debug("Query : " + sqlQuery);
            return jdbcTemplate.query(sqlQuery, new Object[] {"name" + '%', limit, offset},
                    new int[] {Types.VARCHAR, Types.INTEGER, Types.INTEGER},
                    new ComputerRowMapper());
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to correctly find all entities.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    /**
     * Sets values in the prepared statement.
     *
     * @param ps statement to be loaded
     * @param computer computer with values to load in statement
     * @throws SQLException SQL exception
     */
    public void setStatementValues(PreparedStatement ps, Computer computer) throws SQLException {
        LOGGER.info("Set values in statement");
        Company company = computer.getManufacturer();

        ps.setString(1, computer.getName());
        ps.setLong(4, company.getId());

        if (computer.getIntroduced() != null) {
            ps.setDate(2, Date.valueOf(computer.getIntroduced()));
        } else {
            ps.setDate(2, null);
        }

        if (computer.getDiscontinued() != null) {
            ps.setDate(3, Date.valueOf(computer.getDiscontinued()));
        } else {
            ps.setDate(3, null);
        }
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
