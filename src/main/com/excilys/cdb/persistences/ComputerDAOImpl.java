package com.excilys.cdb.persistences;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.models.Company;
import com.excilys.cdb.models.Computer;

public class ComputerDAOImpl implements ComputerDAO {
	private final String FIND_QUERY = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id, "
									+ "com.name AS company_name "
									+ "FROM computer AS  cpu "
									+ "LEFT JOIN company AS com ON cpu.company_id = com.id WHERE cpu.id = ?";
	private final String FIND_ALL = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id, "
									+ "com.name AS company_name "
									+ "FROM computer AS  cpu "
									+ "LEFT JOIN company as com ON cpu.company_id = com.id";
	private final String COUNT_QUERY = "SELECT COUNT(id) FROM computer";
	private final String CREATE_QUERY = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private final String DELETE_QUERY = "DELETE FROM computer WHERE computer.id = ?";
	private final String UPDATE_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final String BOUNDED_RST = " LIMIT ? OFFSET ?";
	
	private List<Computer> computersList;
	private Computer computer;
	private Connection connection;

	@Override
	public Computer create(Computer obj) {
		connection = Connector.INSTANCE.getConnection();
		try (
				PreparedStatement ps = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
				){
			if(obj != null) {
				setStatementValues(ps, obj);
				if(ps.executeUpdate() != 0) {
					obj.setId(ps.getGeneratedKeys().getLong(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Connector.INSTANCE.disconnect();
		}
		
		return obj;
	}

	@Override
	public void delete(Computer obj) {
		Long id = obj.getId();
		connection = Connector.INSTANCE.getConnection();
		
		if(findById(id) != null) {
			try (
					PreparedStatement ps = connection.prepareStatement(DELETE_QUERY);
					){
				ps.setLong(1, id);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				Connector.INSTANCE.disconnect();
			}
		}
	}

	@Override
	public List<Computer> findAll() {
		computer = null;
		computersList = new ArrayList<Computer>();
		connection = Connector.INSTANCE.getConnection();
		
		try (
				PreparedStatement ps = connection.prepareStatement(FIND_ALL);
				ResultSet rs = ps.executeQuery();
				){
			while(rs.next()){
				computersList.add(loadComputer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Connector.INSTANCE.disconnect();
		}
		
		return computersList;
	}

	@Override
	public Computer findById(Long id) {
		computer = null;
		connection = Connector.INSTANCE.getConnection();
		
		try (
				PreparedStatement ps = connection.prepareStatement(FIND_QUERY);
				){
			if(id != null) {			
				ps.setLong(1, id);
				if(ps.executeUpdate() != 0) {
					loadComputer(ps.getResultSet());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Connector.INSTANCE.disconnect();
		}
		
		return computer;
	}

	@Override
	public void update(Computer obj) {
		connection = Connector.INSTANCE.getConnection();
		try (
				PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
				){
			if(obj != null) {
				if(findById(obj.getId()) != null) {
					setStatementValues(ps, obj);
					ps.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			Connector.INSTANCE.disconnect();
		}
	}

	public List<Computer> findAll(int limit, int offset) {
		computer = null;
		computersList = new ArrayList<Computer>();
		connection = Connector.INSTANCE.getConnection();
		
		try (
				PreparedStatement ps = connection.prepareStatement(FIND_ALL+BOUNDED_RST);
				){
			ps.setInt(1, limit);
			ps.setInt(2, offset);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				computersList.add(loadComputer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Connector.INSTANCE.disconnect();
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
			LocalDate i = rs.getDate("introduced").toLocalDate();
			LocalDate d = rs.getDate("discontinued").toLocalDate();
			
			com = new Company.Builder(rs.getString("company_name")).id(rs.getLong("company_id")).build();
			computer = new Computer.Builder(rs.getString("name")).id(rs.getLong("id")).introduced(i).discontinued(d).manufacturer(com).build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computer;
	}
	
	private void setStatementValues(PreparedStatement ps, Computer computer) throws SQLException {
		Long comId = computer.getManufacturer().getId();

		ps.setString(1, computer.getName());
		ps.setString(2, computer.getIntroduced().toString());
		ps.setString(3, computer.getDiscontinued().toString());
		ps.setLong(4, comId);
	}
}
