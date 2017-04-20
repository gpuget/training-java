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
    private static final String BOUNDED_RST = " LIMIT ? OFFSET ?";
    private static final String COUNT_QUERY = "SELECT COUNT(id) FROM computer";

    private List<Computer> computersList;
    private Computer computer;
    private Connection connection;

    @Override
    public Computer create(Computer obj) {
        connection = Connector.INSTANCE.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CREATE_QUERY,
                Statement.RETURN_GENERATED_KEYS);) {
            if (obj != null) {
                setStatementValues(ps, obj);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.first()) {
                    obj.setId(rs.getLong(1));
                    rs.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connector.INSTANCE.disconnect();
        }

        return obj;
    }

    @Override
    public void delete(Long id) {
        if (id != null && findById(id) != null) {
            connection = Connector.INSTANCE.getConnection();
            try (PreparedStatement ps = connection
                    .prepareStatement(DELETE_QUERY);) {
                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                Connector.INSTANCE.disconnect();
            }
        }
    }

    @Override
    public List<Computer> findAll() {
        computer = null;
        computersList = new ArrayList<Computer>();
        connection = Connector.INSTANCE.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL);
                ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                computersList.add(loadComputer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connector.INSTANCE.disconnect();
        }

        return computersList;
    }

    @Override
    public List<Computer> findAll(int limit, int offset) {
        computer = null;
        computersList = new ArrayList<Computer>();
        connection = Connector.INSTANCE.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL
                + BOUNDED_RST);) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                computersList.add(loadComputer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connector.INSTANCE.disconnect();
        }

        return computersList;
    }

    @Override
    public Computer findById(Long id) {
        computer = null;
        connection = Connector.INSTANCE.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(FIND_QUERY);) {
            if (id != null) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.first()) {
                    loadComputer(rs);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connector.INSTANCE.disconnect();
        }

        return computer;
    }

    @Override
    public void update(Computer obj) {
        if (obj != null) {
            Long id = obj.getId();
            if (id != null && findById(id) != null) {
                connection = Connector.INSTANCE.getConnection();
                try (PreparedStatement ps = connection
                        .prepareStatement(UPDATE_QUERY);) {
                    setStatementValues(ps, obj);
                    ps.setLong(5, id);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    Connector.INSTANCE.disconnect();
                }
            }
        }

    }

    @Override
    public int getTotal() {
        connection = Connector.INSTANCE.getConnection();
        int res = 0;
        
        try (PreparedStatement ps = connection.prepareStatement(COUNT_QUERY);) {
            ResultSet rs = ps.executeQuery();
            if(rs.first()){
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        Company com = new Company.Builder(rs.getString("company_name")).id(
                rs.getLong("company_id")).build();
        Timestamp i = rs.getTimestamp("introduced");
        Timestamp d = rs.getTimestamp("discontinued");
        
        computer = new Computer.Builder(rs.getString("name"))
                                .id(rs.getLong("id"))
                                .manufacturer(com)
                                .build();
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
        if (company != null) {
            ps.setLong(4, company.getId());
        }
    }
}
