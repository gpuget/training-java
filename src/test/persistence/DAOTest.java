package persistence;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import model.Company;
import model.Computer;

public class DAOTest {
	private List<DAO> DAOList;
	
	@Before
	public void init() {
		DAOList = new ArrayList<>();
		DAOList.add(new CompanyDAO());
		DAOList.add(new ComputerDAO());
	}
	
	@After
	public void end() {
		DAOList.removeAll(DAOList);
		DAOList = null;
	}
	
	@Test
	public void DAOSameConnection() {
		assertSame(DAOList.get(0).connection, DAOList.get(1).connection);
	}
	
	@Test
	public void ComputerDAOFindNotNull() throws SQLException {
		assertNotNull(DAOList.get(1).findById(1l));
	}
	
	@Test
	public void ComputerDAOFind() throws SQLException {
		//Apple Inc. --> 1
		//Apple III. --> 12
		Computer c = (Computer) DAOList.get(1).findById(12l);
		Computer c1 = new Computer(12l, "ELF II", new Company(4l, "Netronics"), Date.valueOf("1977-1-1"));
		Computer c2 = new Computer(12l, "Apple III", new Company(1l, "Apple Inc."), Date.valueOf("1980-05-01"));
		System.out.println(c);
		System.out.println(c2);
		System.out.println(c1);
		assertNotEquals(c1, c);
		assertEquals(c2, c);
	}

	@Test
	public void ComputerDAOFindAll() throws SQLException {
		//SELECT COUNT(*) FROM computer --> 574
		assertEquals(574, DAOList.get(1).findAll().size());
	}
	
}
