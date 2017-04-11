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
									+ "LEFT OUTER JOIN company AS com ON cpu.company_id = com.id WHERE cpu.id = ?";
	private final String FIND_ALL = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id, "
									+ "com.name AS company_name "
									+ "FROM computer AS  cpu "
									+ "LEFT OUTER JOIN company as com ON cpu.id = com.id";
	private final String CREATE_QUERY = "INSERT INTO computer VALUES (?, ?, ?, ?, ?)";
	private final String DELETE_QUERY = "DELETE FROM computer WHERE computer.id = ?";
	
	private Collection<Computer> computersList = null;
	private Computer computer = null;
	
	public ComputerDAO() {
		super();
	}

	@Override
	public Computer create(Computer obj) {
		
		try {
			if(obj != null) {
				long id = obj.getId();
				long comId = obj.getManufacturer().getId();
				
				if(findById(id)==null) {
					PreparedStatement ps = connection.prepareStatement(CREATE_QUERY);
					ps.setLong(1, obj.getId());
					ps.setString(2, obj.getName());
					ps.setDate(3, obj.getIntroduced());
					ps.setDate(4, obj.getDiscontinued());
					if(comId ==0l) ps.setString(5, null);
					else ps.setLong(5, comId);
					ps.execute();
				}else obj=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

	@Override
	public boolean delete(Computer obj) {
		long id = obj.getId();
		if(findById(id) != null) {
			try {
				PreparedStatement ps = connection.prepareStatement(DELETE_QUERY);
				ps.setLong(1, id);
				ps.execute();
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
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
