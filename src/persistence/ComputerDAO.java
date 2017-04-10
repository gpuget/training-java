package persistence;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import model.Company;
import model.Computer;

public class ComputerDAO extends DAO<Computer> {
	private final String FIND_QUERY = "SELECT cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id, com.name AS company_name FROM computer AS  cpu INNER JOIN company AS com ON cpu.company_id = com.id WHERE cpu.id = ?";
	
	public ComputerDAO() {
		super();
	}

	@Override
	public Computer create(Computer obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Computer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Computer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Computer findById(long id) {
		PreparedStatement ps;
		Computer cpu = null;
		
		try {
			ps = connection.prepareStatement(FIND_QUERY);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.first()) {
				Company com = new Company(rs.getLong(4), rs.getString(5));
				cpu = new Computer(id, rs.getString(1), com, rs.getDate(2), rs.getDate(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cpu;
	}

	@Override
	public Computer update(Computer obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
