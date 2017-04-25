package com.excilys.cdb.persistences;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.models.Company;

public class CompanyDAOImpl implements CompanyDAO {
    private static final String FIND_ALL = "SELECT id, name FROM company";
    private static final String FIND_QUERY = "SELECT com.id, com.name "
            + "FROM company AS  com "
            + "WHERE com.id = ?";

    @Override
    public List<Company> findAll() {
        Connection connection = Connector.INSTANCE.getConnection();
        ArrayList<Company> companiesList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL);
                ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                companiesList.add(new Company.Builder()
                                                .id(resultSet.getLong("id"))
                                                .name(resultSet.getString("name")).build());
            }
        } catch (SQLException e) {
            throw new DAOException("Error : DAO has not been able to correctly find all entities.", e);
        } finally {
            Connector.INSTANCE.disconnect();
        }

        return companiesList;
    }

    @Override
    public Company findById(long id) {
        Company company = null;
        Connection connection = Connector.INSTANCE.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(FIND_QUERY);) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.first()) {
                company = loadCompany(rs);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error : DAO has not been able to find the entity.", e);
        } finally {
            Connector.INSTANCE.disconnect();
        }

        return company;
    }
    
    private Company loadCompany(ResultSet rs) throws SQLException {
        return new Company.Builder().id(rs.getLong("id"))
                                    .name(rs.getString("name")).build();
    }
}
