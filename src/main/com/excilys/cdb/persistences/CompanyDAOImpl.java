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
	
	private List<Company> companiesList;
	private Company company;
	private Connection connection;

	@Override
	public List<Company> findAll() {
		PreparedStatement ps;
		ResultSet rs;
		connection = Connector.INSTANCE.getConnection();
		company = null;
		companiesList = new ArrayList<>();
		
		try {
			ps = connection.prepareCall(FIND_ALL);
			ps.execute();
			rs = ps.getResultSet();
			while(rs.next()){
				company = new Company.Builder(rs.getString("name")).id(rs.getLong("id")).build();
				companiesList.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return companiesList;
	}
}
