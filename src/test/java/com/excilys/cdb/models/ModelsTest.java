package com.excilys.cdb.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModelsTest {
	@Test
	public void computerEquals() {
		Company com1 = new Company.Builder("CPY1").build();
		Company com2 = new Company.Builder("CPY2").build();
		Computer cpu1 = new Computer.Builder("CPU1")
									.manufacturer(com1)
									.build();
		Computer cpu2 = new Computer.Builder("CPU2")
									.manufacturer(com2)
									.build();
		Computer cpu3 = new Computer.Builder("CPU3")
									.manufacturer(com1)
									.build();
		Computer cpu4 = new Computer.Builder("CPU1")
									.manufacturer(com1)
									.build();
		
		assertTrue(cpu1.equals(cpu4));
		assertFalse(cpu1.equals(cpu2));
		assertFalse(cpu1.equals(cpu3));
		assertTrue(cpu1.equals(cpu4));
	}
	
	@Test
	public void companyEquals() {
		Company com1 = new Company.Builder("CPY1").build();
		Company com2 = new Company.Builder("CPY2").build();
		Company com3 = new Company.Builder("CPY1").build();
		
		assertTrue(com1.equals(com3));
		assertFalse(com1.equals(com2));
	}
}
