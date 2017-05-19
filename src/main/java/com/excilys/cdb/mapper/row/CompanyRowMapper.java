package com.excilys.cdb.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;

public class CompanyRowMapper implements RowMapper<Company> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRowMapper.class);

    @Override
    public Company mapRow(ResultSet rs, int row) throws SQLException {
        LOGGER.info("Map a company row : " + row);
        return mapCompany(rs);
    }

    /**
     * Create a Company with a result set.
     *
     * @param rs provided result set
     * @return mapped company
     * @throws SQLException if result set is not validate
     */
    public static Company mapCompany(ResultSet rs) throws SQLException {
        LOGGER.info("Get a Company from result set");
        return new Company.Builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .build();
    }
}
