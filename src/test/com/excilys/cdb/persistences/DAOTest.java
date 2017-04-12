package com.excilys.cdb.persistences;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.models.Company;
import com.excilys.cdb.models.Computer;
import com.excilys.cdb.persistences.CompanyDAOImpl;
import com.excilys.cdb.persistences.ComputerDAOImpl;
import com.excilys.cdb.persistences.DAO;

public class DAOTest {
	private List<DAO<?>> DAOList;
	
	@Before
	public void init() {
		DAOList = new ArrayList<>();
		DAOList.add(CompanyDAOImpl.getInstance());
		DAOList.add(ComputerDAOImpl.getInstance());
	}
	
	@After
	public void end() {
		DAOList.removeAll(DAOList);
		DAOList = null;
	}
	
	@Test
	public void computerDAOFindNotNull() throws SQLException {
		assertNotNull(DAOList.get(1).findById(1l));
	}
	
	@Test
	public void computerDAOFind() throws SQLException {
		//Apple Inc. --> 1
		//Apple III. --> 12
		Computer c = (Computer) DAOList.get(1).findById(12l);
		Computer c1 = new Computer(12l, "ELF II", new Company(4l, "Netronics"), Date.valueOf("1977-1-1"));
		Computer c2 = new Computer(12l, "Apple III", new Company(1l, "Apple Inc."), Date.valueOf("1980-05-01"));
		assertNotEquals(c1, c);
		assertEquals(c2, c);
	}

	@Test
	public void computerDAOFindAll() throws SQLException {
		//SELECT COUNT(*) FROM computer --> 574
		assertEquals(574, DAOList.get(1).findAll().size());
		assertEquals(new Computer(12l, "Apple III", new Company(1l, "Apple Inc."), Date.valueOf("1980-05-01")), DAOList.get(1).findAll().get(11));
	}
	
	@Test
	public void computerDAOFindBounded() {
		ComputerDAOImpl dao = (ComputerDAOImpl) DAOList.get(1);
		
		assertEquals(5, dao.findAll(5, 100).size());
		assertTrue(dao.findAll(0, 100).isEmpty());
		assertTrue(dao.findAll(5, 1000).isEmpty());
	}
	
	@Test public void computerDAOInsertAndDelete() throws SQLException {
		ComputerDAOImpl dao = (ComputerDAOImpl) DAOList.get(1);
		assertNotNull(dao.create(new Computer(777l, "Test", new Company(null, null))));
		assertNull(dao.create(new Computer(777l, "Test", new Company(null, null))));	
		assertNotNull(dao.findById(777l));	

		assertTrue(dao.delete(new Computer(777l, "Test", new Company(null, null))));
		assertFalse(dao.delete(new Computer(777l, "Test", new Company(null, null))));
		assertNull(dao.findById(777l));
	}
	
	@Test
	public void companyDAOUpdate() {
		//| 574 | iPhone 4S | 2011-10-14 00:00:00 | NULL         |          1 |
		//| 1 | Apple Inc. |
		ComputerDAOImpl dao = (ComputerDAOImpl) DAOList.get(1);
		Computer c, c2, c3;
		
		assertNull(dao.update(new Computer(777l, "", new Company(null, null))));
		c = dao.findById(574l);
		assertNotNull(dao.update(new Computer(574l, "", new Company(1l, "Apple Inc."))));
		c2 = dao.findById(574l);		
		assertNotNull(dao.update(new Computer(574l, "iPhone 4S", new Company(1l, "Apple Inc."), Date.valueOf("2011-10-14"))));
		c3 = dao.findById(574l);
		assertEquals(c, c3);
		assertNotEquals(c, c2);
	}
	
	@Test
	public void companyDAOFindAll() throws SQLException {
		//SELECT COUNT(*) FROM company --> 42
		assertEquals(42, DAOList.get(0).findAll().size());
	} 
}
