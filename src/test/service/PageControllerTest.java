package service;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Company;
import model.Computer;
import model.Page;

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
	public void pageCreate() {
		assertNull(pc.getPage());
		pc.createPage();
		assertNotNull(pc.getPage());
		assertEquals(1, pc.getPage().getNumber());
		assertEquals(Page.MAX_PER_PAGE, pc.getPage().getComputers().size());
		System.out.println(pc.getPage());
		assertTrue(pc.getPage().getComputers().contains(new Computer(12l, "Apple III", new Company(1l, "Apple Inc."), Date.valueOf("1980-05-01"))));
	}
}
