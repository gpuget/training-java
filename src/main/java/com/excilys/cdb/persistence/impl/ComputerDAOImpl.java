package com.excilys.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Computer create(Computer computer) {
        LOGGER.info("Create computer : " + computer);
        String message = "Error : DAO has not been able to correctly create the entity.";

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS);) {
            setStatementValues(statement, computer);
            LOGGER.debug("Query : " + statement);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.first()) {
                    long id = rs.getLong(1);
                    computer.setId(id);
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

        return computer;
    }

    @Override
    public void delete(long id) {
        LOGGER.info("Delete computer by id : " + id);
        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
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
    public void delete(List<Long> idsList) {
        LOGGER.info("Delete all computer in : " + idsList);
        StringBuilder sb = new StringBuilder();

        sb.append(idsList.get(0));
        for (int i = 1; i < idsList.size(); i++) {
            sb.append(", ").append(idsList.get(i));
        }

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(DELETE_IN + '(' + sb.toString() + ')')) {
            LOGGER.debug("Query : " + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            String message = "Error : DAO has not been able to correctly delete entities.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public void deleteFromCompany(long companyId, Connection connection) {
        LOGGER.info("Delete computer from company : " + companyId);
        LOGGER.debug("Connection used : " + connection);
        try (PreparedStatement statement = connection.prepareStatement(DELETE_FROM_COMPANY)) {
            statement.setLong(1, companyId);
            LOGGER.debug("Query : " + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            String message = "Error : DAO has not been able to correctly delete entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public List<Computer> findAll() {
        LOGGER.info("Find all computers.");
        ArrayList<Computer> computers = new ArrayList<>();

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL);
                ResultSet resultSet = statement.executeQuery()) {
            LOGGER.debug("Query : " + statement);
            while (resultSet.next()) {
                computers.add(ComputerRowMapper.mapComputer(resultSet));
            }
            LOGGER.debug("Found computers : " + computers);
        } catch (SQLException e) {
            String message = "Error : DAO has not been able to correctly find all entities.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return computers;
    }

    @Override
    public List<Computer> findAll(int limit, int offset) {
        LOGGER.info("Find all computers : " + limit + " " + offset);
        ArrayList<Computer> computers = new ArrayList<Computer>();

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(FIND_ALL + BOUNDED_RESULT)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            LOGGER.debug("Query : " + statement);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    computers.add(ComputerRowMapper.mapComputer(resultSet));
                }
            }
            LOGGER.debug("Found computers : " + computers);
        } catch (SQLException e) {
            String message = "Error : DAO has not been able to correctly find all entities.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return computers;
    }

    @Override
    public Computer findById(long id) {
        LOGGER.info("Find computer by id : " + id);
        Computer computer = null;

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_QUERY)) {
            statement.setLong(1, id);
            LOGGER.debug("Query : " + statement);
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.first()) {
                    computer = ComputerRowMapper.mapComputer(resultSet);
                    LOGGER.debug("Found computer : " + computer);
                }
            }
        } catch (SQLException e) {
            String message = "Error : DAO has not been able to find the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return computer;
    }

    @Override
    public Computer update(Computer computer) {
        LOGGER.info("Update computer :" + computer);
        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setStatementValues(statement, computer);
            statement.setLong(5, computer.getId());
            LOGGER.debug("Query : " + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
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
        int res = 0;

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(COUNT_QUERY);
                ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.first()) {
                res = resultSet.getInt(1);
            } else {
                LOGGER.error(message);
                throw new DAOException(message);
            }
        } catch (SQLException e) {
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return res;
    }

    @Override
    public List<Computer> getFilteredByName(int limit, int offset, String name) {
        LOGGER.info("Find all computer with name : " + name);
        ArrayList<Computer> computers = new ArrayList<Computer>();

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(FIND_ALL + LIKE_NAME + BOUNDED_RESULT)) {
            statement.setString(1, name + '%');
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            LOGGER.debug("Query : " + statement);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    computers.add(ComputerRowMapper.mapComputer(resultSet));
                }
                LOGGER.debug("Found computers : " + computers);
            }
        } catch (SQLException e) {
            String message = "Error : DAO has not been able to correctly find all entities.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return computers;
    }

    /**
     * Sets values in the prepared statement.
     *
     * @param ps statement to be loaded
     * @param computer computer with values to load in statement
     * @throws SQLException SQL exception
     */
    private void setStatementValues(PreparedStatement ps, Computer computer) throws SQLException {
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
