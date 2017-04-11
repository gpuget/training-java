package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;

public class CompanyDAO extends DAO<Company> {
	private final String FIND_ALL = "SELECT id, name FROM company";
	
	private List<Company> companiesList = null;
	private Company company = null;
	private static CompanyDAO instance = null;
	
	private CompanyDAO() {
		super();
	}
	
	public static CompanyDAO getInstance() {
		if(instance == null) instance = new CompanyDAO();
		return instance;
	}

	@Override
	public Company create(Company obj) {return null;}

	@Override
	public boolean delete(Company obj) {return false;}

	@Override
	public List<Company> findAll() {
		company = null;
		companiesList = new ArrayList<>();		
		try {
			PreparedStatement ps = connection.prepareCall(FIND_ALL);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()){
				company = new Company(rs.getLong("id"), rs.getString("name"));
				companiesList.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return companiesList;
	}

	@Override
	public Company findById(long id) {return null;}

	@Override
	public Company update(Company obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
