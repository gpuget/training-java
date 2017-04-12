package com.excilys.cdb.services;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.models.Company;
import com.excilys.cdb.models.Computer;
import com.excilys.cdb.models.Page;
import com.excilys.cdb.services.PageController;

public class PageControllerTest {
	private PageController pc = null;
	
	@Before
	public void init() {
		this.pc = PageController.getInstance();
	}
	
	@After
	public void end() {
		this.pc = null;
	}
	
	@Test
	public void pageControllerCreate() {
		assertNull(pc.getPage());
		pc.createPage();
		assertNotNull(pc.getPage());
		assertEquals(1, pc.getPage().getNumber());
		assertEquals(Page.MAX_PER_PAGE, pc.getPage().getComputers().size());
		assertTrue(pc.getPage().getComputers().contains(new Computer(20l, "ELF II", new Company(4l, "Netronics"), Date.valueOf("1977-01-01"))));
		assertFalse(pc.getPage().getCompanies().isEmpty());
	}
	
	@Test
	public void pageControllerNextAndPrevious() {
		pc.createPage();
		assertEquals(2, pc.nextPage());
		assertFalse(pc.getPage().getComputers().contains(new Computer(20l, "ELF II", new Company(4l, "Netronics"), Date.valueOf("1977-01-01"))));
		assertTrue(pc.getPage().getComputers().contains(new Computer(40l, "Macintosh LC II", new Company(1l, "Apple Inc."), Date.valueOf("1990-01-01"))));
		
		assertEquals(1, pc.previousPage());
		assertTrue(pc.getPage().getComputers().contains(new Computer(20l, "ELF II", new Company(4l, "Netronics"), Date.valueOf("1977-01-01"))));
		assertFalse(pc.getPage().getComputers().contains(new Computer(40l, "Macintosh LC II", new Company(1l, "Apple Inc."), Date.valueOf("1990-01-01"))));
	}
}
