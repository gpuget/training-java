package com.excilys.cdb.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerRowMapper.class);

    @Override
    public Computer mapRow(ResultSet rs, int row) throws SQLException {
        LOGGER.info("Map a computer row : " + row);
        return mapComputer(rs);
    }

    /**
     * Loads the computer resulting from the query in an object Computer.
     *
     * @param rs result of the query
     * @return object Computer
     * @throws SQLException SQL exception
     */
    public static Computer mapComputer(ResultSet rs) throws SQLException {
        LOGGER.info("Get a Computer from result set");
        Computer computer;
        Company com = new Company.Builder().id(rs.getLong("company_id"))
                .name(rs.getString("company_name")).build();
        Timestamp i = rs.getTimestamp("introduced");
        Timestamp d = rs.getTimestamp("discontinued");
        computer = new Computer.Builder().id(rs.getLong("id")).name(rs.getString("name"))
                .manufacturer(com).build();
        if (i != null) {
            computer.setIntroduced(i.toLocalDateTime().toLocalDate());
        }
        if (d != null) {
            computer.setDiscontinued(d.toLocalDateTime().toLocalDate());
        }

        return computer;
    }
}
