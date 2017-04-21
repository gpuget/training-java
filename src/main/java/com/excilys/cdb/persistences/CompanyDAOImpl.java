package com.excilys.cdb.persistences;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.models.Company;

public class CompanyDAOImpl implements CompanyDAO {
    public static final String FIND_ALL = "SELECT id, name FROM company";

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
}
