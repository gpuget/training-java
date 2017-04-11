package persistence;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import model.Company;
import model.Computer;

public class ComputerDAO extends DAO<Computer> {
	private final String FIND_QUERY = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id, "
									+ "com.name AS company_name "
									+ "FROM computer AS  cpu "
									+ "INNER JOIN company AS com ON cpu.company_id = com.id WHERE cpu.id = ?";
	private final String FIND_ALL = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id, "
									+ "com.name AS company_name "
									+ "FROM computer AS  cpu "
									+ "LEFT OUTER JOIN company as com ON cpu.id = com.id";
	
	private Collection<Computer> computersList = null;
	private Computer computer = null;
	
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
		computer = null;
		computersList = new ArrayList<Computer>();
		try {
			CallableStatement s = connection.prepareCall(FIND_ALL);
			s.execute();
			ResultSet rs = s.getResultSet();
			while(rs.next()){
				computersList.add(loadComputer(rs));
				System.out.println(computersList.size()+" "+computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computersList;
	}

	@Override
	public Computer findById(long id) {
		PreparedStatement ps;
		computer = null;
		
		try {
			ps = connection.prepareStatement(FIND_QUERY);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.first()) loadComputer(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return computer;
	}

	@Override
	public Computer update(Computer obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Computer loadComputer(ResultSet rs) {
		Company com = null;
		
		try {
			com = new Company(rs.getLong("company_id"), rs.getString("company_name"));
			computer = new Computer(rs.getLong("id"), rs.getString("name"), com, rs.getDate("introduced"), rs.getDate("discontinued"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computer;
	}

}
