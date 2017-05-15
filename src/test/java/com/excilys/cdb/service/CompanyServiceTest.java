package com.excilys.cdb.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class CompanyServiceTest {
	private CompanyService companyService;
	ComputerService computerService;
	
	@Test
	public void createAndDelete() {
		Company com = new Company.Builder().name("Bob Inc.").build();
		
		com = companyService.create(com);
		companyService.delete(com.getId());
	}
	
	@Test
	public void deleteWithComputer() {		
		Company com = new Company.Builder().name("Bob Inc.").build();
		
		com = companyService.create(com);
		Computer cpu = new Computer.Builder()
				.name("Bob")
				.manufacturer(com)
				.build();
		cpu = computerService.create(cpu);
		assertNotNull(computerService.getDetails(cpu.getId()));
		companyService.delete(com.getId());
		assertNull(computerService.getDetails(cpu.getId()));
		assertNull(companyService.getCompanyById(com.getId()));
	}
}
