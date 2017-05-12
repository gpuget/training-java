package com.excilys.cdb.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.impl.ComputerDAOImpl;
import com.excilys.cdb.service.ComputerService;

public class ComputerServiceTest {
	private ComputerService computerService = ComputerService.INSTANCE;
	
    @Test
    public void correctPage(){        
        assertEquals(10, computerService.getPage(1, 10).getObjects().size());
        assertEquals(new ComputerDAOImpl().findById(12L), computerService.getPage(2, 10).getObjects().get(1));
    }
    
    @Test
    public void createAndDelete() {
    	Computer cpu = new Computer.Builder()
    								.name("Bob") 
    								.manufacturer(new Company.Builder().id(1L).build())
    								.build();
    	
    	cpu = computerService.create(cpu);
    	computerService.delete(cpu.getId());
    }
}
