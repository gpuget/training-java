package com.excilys.cdb.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class BuilderTest {	
	@Test
	public void computerBuilderTest() {
		//Computer c = new Computer(String) --> private
		Computer c = new Computer.Builder("toto").build();
		assertNotNull(c);
		assertNotNull(c.getName());
		assertEquals("toto", c.getName());
		assertNull(c.getId());
		c = new Computer.Builder("toto").id(Long.valueOf(0l)).build();
		assertNotNull(c.getId());
		assertEquals(Long.valueOf(0l), c.getId());
	}
	
	@Test
	public void companyBuilderTest() {
		//Company c = new Company(String) --> private
		Company c = new Company.Builder("toto").build();
		assertNotNull(c);
		assertNotNull(c.getName());
		assertEquals("toto", c.getName());
		assertNull(c.getId());
		c = new Company.Builder("toto").id(Long.valueOf(0l)).build();
		assertNotNull(c.getId());
		assertEquals(Long.valueOf(0l), c.getId());
	}
}
