package com.excilys.cdb.persistences;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.models.Company;
import com.excilys.cdb.models.Computer;

public class ComputerDAOImpl implements ComputerDAO {
    private static final String FIND_QUERY = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id, "
            + "com.name AS company_name "
            + "FROM computer AS  cpu "
            + "LEFT JOIN company AS com ON cpu.company_id = com.id WHERE cpu.id = ?";
    private static final String FIND_ALL = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id, "
            + "com.name AS company_name "
            + "FROM computer AS  cpu "
            + "LEFT JOIN company as com ON cpu.company_id = com.id";
    private static final String CREATE_QUERY = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM computer WHERE computer.id = ?";
    private static final String UPDATE_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(cpu.id) FROM computer AS cpu";
    private static final String BOUNDED_RESULT = " LIMIT ? OFFSET ?";
    private static final String LIKE_NAME = " WHERE cpu.name LIKE ?";

    @Override
    public Computer create(Computer computer) {
        Connection connection = Connector.INSTANCE.getConnection();
        
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
            Statement.RETURN_GENERATED_KEYS);) {
            setStatementValues(statement, computer);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.first()) {
                computer.setId(rs.getLong(1));
                rs.close();
            } else {
                throw new DAOException("Error : DAO has not been able to correctly create the entity.");
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error : DAO has not been able to correctly create the entity.", e);
        } finally {
            Connector.INSTANCE.disconnect();
        }

        return computer;
    }

    @Override
    public void delete(long id) {
        Connection connection = Connector.INSTANCE.getConnection();
        
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error : DAO has not been able to correctly delete the entity.", e);
        } finally {
            Connector.INSTANCE.disconnect();
        }
    }

    @Override
    public List<Computer> findAll() {
        Connection connection = Connector.INSTANCE.getConnection();
        ArrayList<Computer> computers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL);
                ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                computers.add(loadComputer(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("Error : DAO has not been able to correctly find all entities.", e);
        } finally {
            Connector.INSTANCE.disconnect();
        }

        return computers;
    }

    @Override
    public List<Computer> findAll(int limit, int offset) {
        Connection connection = Connector.INSTANCE.getConnection();
        ArrayList<Computer> computers = new ArrayList<Computer>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL
                + BOUNDED_RESULT);) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                computers.add(loadComputer(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("Error : DAO has not been able to correctly find all entities.", e);
        } finally {
            Connector.INSTANCE.disconnect();
        }

        return computers;
    }

    @Override
    public Computer findById(long id) {
        Computer computer = null;
        Connection connection = Connector.INSTANCE.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FIND_QUERY);) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.first()) {
                computer = loadComputer(rs);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error : DAO has not been able to find the entity.", e);
        } finally {
            Connector.INSTANCE.disconnect();
        }

        return computer;
    }

    @Override
    public void update(Computer computer) {
        Connection connection = Connector.INSTANCE.getConnection();
        
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);) {
            setStatementValues(statement, computer);
            statement.setLong(5, computer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error : DAO has not been able to correctly update the entity.", e);
        } finally {
            Connector.INSTANCE.disconnect();
        }
    }

    @Override
    public int getCount() {
        Connection connection = Connector.INSTANCE.getConnection();
        
        int res = 0;
        
        try (PreparedStatement statement = connection.prepareStatement(COUNT_QUERY);) {
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.first()){
                res = resultSet.getInt(1);
                resultSet.close();
            } else {
                throw new DAOException("Error : DAO has not been able to correctly count the entities.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error : DAO has not been able to correctly count the entities.", e);
        } finally {
            Connector.INSTANCE.disconnect();
        }
        return res;
    }

    @Override
    public List<Computer> getFilteredByName(int limit, int offset, String name) {
        Connection connection = Connector.INSTANCE.getConnection();
        ArrayList<Computer> computers = new ArrayList<Computer>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL + LIKE_NAME + BOUNDED_RESULT);) {
            statement.setString(1, '%'+name+'%');
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                computers.add(loadComputer(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("Error : DAO has not been able to correctly find all entities.", e);
        } finally {
            Connector.INSTANCE.disconnect();
        }

        return computers;
    }

    @Override
    public int getFilteredByNameCount(String name) {
        Connection connection = Connector.INSTANCE.getConnection();
        
        int res = 0;
        
        try (PreparedStatement statement = connection.prepareStatement(COUNT_QUERY + LIKE_NAME);) {
            statement.setString(1, '%'+name+'%');
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.first()){
                res = resultSet.getInt(1);
                resultSet.close();
            } else {
                throw new DAOException("Error : DAO has not been able to correctly count the entities.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error : DAO has not been able to correctly count the entities.", e);
        } finally {
            Connector.INSTANCE.disconnect();
        }
        return res;
    }

    /**
     * Loads the computer resulting from the query in an object Computer.
     * 
     * @param rs
     *            result of the query
     * @return object Computer
     * @throws SQLException
     *             SQL exception
     */
    private Computer loadComputer(ResultSet rs) throws SQLException {
        Company com = new Company.Builder()
                                    .id(rs.getLong("company_id"))
                                    .name(rs.getString("company_name")).build();
        Timestamp i = rs.getTimestamp("introduced");
        Timestamp d = rs.getTimestamp("discontinued");
        
        Computer computer = new Computer.Builder()
                                .id(rs.getLong("id"))
                                .name(rs.getString("name"))
                                .manufacturer(com).build();
        if(i != null) {
            computer.setIntroduced(i.toLocalDateTime().toLocalDate());
        }
        
        if(d != null) {
            computer.setDiscontinued(d.toLocalDateTime().toLocalDate());
        }
        
        return computer;
    }

    /**
     * Sets values in the prepared statement.
     * 
     * @param ps
     *            statement to be loaded
     * @param computer
     *            computer with values to load in statement
     * @throws SQLException
     *             SQL exception
     */
    private void setStatementValues(PreparedStatement ps, Computer computer)
            throws SQLException {
        Company company = computer.getManufacturer();

        ps.setString(1, computer.getName());
        ps.setObject(2, computer.getIntroduced());
        ps.setObject(3, computer.getDiscontinued());
        ps.setLong(4, company.getId());
    }
}
