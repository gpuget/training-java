package com.excilys.cdb.persistences;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.models.Company;
import com.excilys.cdb.models.Computer;

public class DAOTest {
	private ComputerDAO computerDao;
	
	@Before
	public void init() {
		computerDao = new ComputerDAOImpl();
	}
	
	@After
	public void end() {		
		Connector.INSTANCE.disconnect();
		System.gc();
	}
	
	@Test
	public void companyFindAll() {
		// SELECT COUNT(*) FROM company --> 42 (Answer to life, the universe and everything)
		// 1 Apple Inc.
		CompanyDAO companyDao = new CompanyDAOImpl();
		
		assertTrue(Connector.INSTANCE.isDisconnected());
		assertNotNull(companyDao);
		assertNotNull(companyDao.findAll());
		assertEquals(42, companyDao.findAll().size());
		assertEquals("Apple Inc.", companyDao.findAll().get(0).getName());
		assertTrue(Connector.INSTANCE.isDisconnected());
	}
	
	@Test
	public void computerFindId() {
		// 1	Apple Inc.
		// 568	IPad 2		
		assertTrue(Connector.INSTANCE.isDisconnected());
		assertEquals(new Computer.Builder("IPad 2")
									.manufacturer(new Company.Builder("Apple Inc.")
																.id(1L)
																.build())
									.build(), computerDao.findById(568L));
		assertTrue(Connector.INSTANCE.isDisconnected());
	}
	
	@Test
	public void computerCreateAndDelete() {
		Computer c = new Computer.Builder("CPU")
									.manufacturer(new Company.Builder("CPY").id(1L).build())
									.build();
		Computer c2;
		
		assertTrue(Connector.INSTANCE.isDisconnected());
		assertNull(c.getId());
		c2 = computerDao.create(c);
		assertEquals(c, c2);
		assertNotNull(c2.getId());
		assertNotNull(computerDao.findById(c2.getId()));
		computerDao.delete(c2.getId());
		assertNull(computerDao.findById(c2.getId()));
		assertTrue(Connector.INSTANCE.isDisconnected());
	}
	
	@Test
	public void computerUpdate() {
		Computer c = new Computer.Builder("CPU")
				.manufacturer(new Company.Builder("CPY").id(1L).build())
				.build();
		
		assertTrue(Connector.INSTANCE.isDisconnected());
		c = computerDao.create(c);
		computerDao.update(new Computer.Builder("CPU2")
										.id(c.getId())
										.manufacturer(new Company.Builder("CPY2").id(2L).build())
										.build());
		assertNotEquals(c, computerDao.findById(c.getId()));
		computerDao.delete(c.getId());
		assertTrue(Connector.INSTANCE.isDisconnected());
	}
	
	@Test
	public void computerFindAll() {
		// SELECT COUNT(*) FROM computer --> 574
		List<Computer> list;
		
		assertTrue(Connector.INSTANCE.isDisconnected());
		list = computerDao.findAll();
		assertFalse(list.isEmpty());
		assertEquals(574, list.size());
		list = computerDao.findAll(1,1);
		assertEquals(1, list.size());
		assertFalse(list.contains(computerDao.findById(1L)));
		assertTrue(list.contains(computerDao.findById(2L)));
		assertFalse(list.contains(computerDao.findById(3L)));
		assertTrue(Connector.INSTANCE.isDisconnected());		
	}
}
