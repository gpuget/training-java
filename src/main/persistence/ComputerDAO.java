package persistence;

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
									+ "LEFT JOIN company AS com ON cpu.company_id = com.id WHERE cpu.id = ?";
	private final String FIND_ALL = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id, "
									+ "com.name AS company_name "
									+ "FROM computer AS  cpu "
									+ "LEFT JOIN company as com ON cpu.id = com.id";
	private final String COUNT_QUERY = "SELECT COUNT(id) FROM computer";
	private final String CREATE_QUERY = "INSERT INTO computer VALUES (?, ?, ?, ?, ?)";
	private final String DELETE_QUERY = "DELETE FROM computer WHERE computer.id = ?";
	private final String UPDATE_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final String BOUNDED_RST = " LIMIT ? OFFSET ?";
	
	private Collection<Computer> computersList = null;
	private Computer computer = null;
	private static ComputerDAO instance = null;
	
	private ComputerDAO() {
		super();
	}
	
	public static ComputerDAO getInstance() {
		if(instance == null) instance = new ComputerDAO();
		return instance;
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
			PreparedStatement ps = connection.prepareCall(FIND_ALL);
			ps.execute();
			ResultSet rs = ps.getResultSet();
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
			if(id != 0l) {
				ps = connection.prepareStatement(FIND_QUERY);
				ps.setLong(1, id);
				ResultSet rs = ps.executeQuery();
				
				if(rs.first()) loadComputer(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return computer;
	}

	@Override
	public Computer update(Computer obj) {
		try {
			if(obj != null) {
				long id = obj.getId();
				long comId = obj.getManufacturer().getId();
				
				if(findById(id)!=null) {
					PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
					ps.setString(1, obj.getName());
					ps.setDate(2, obj.getIntroduced());
					ps.setDate(3, obj.getDiscontinued());
					if(comId == 0l) ps.setString(4, null);
					else ps.setLong(4, comId);
					ps.setLong(5, id);
					ps.execute();
				}else obj=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

	public Collection<Computer> findAll(int limit, int offset) {
		computer = null;
		computersList = new ArrayList<Computer>();
		try {
			PreparedStatement ps = connection.prepareCall(FIND_ALL+BOUNDED_RST);
			ps.setInt(1, limit);
			ps.setInt(2, offset);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()){
				computersList.add(loadComputer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computersList;
	}
	
	public int count() {
		try {
			PreparedStatement ps = connection.prepareCall(COUNT_QUERY);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if(rs.first()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
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
