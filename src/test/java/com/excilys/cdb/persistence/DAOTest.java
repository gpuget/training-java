package com.excilys.cdb.persistence;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.impl.ComputerDAOImpl;

public class DAOTest {
	private ComputerDAOImpl computerDao = ComputerDAOImpl.INSTANCE;
	
	@Test
	public void correctComputerListSize(){
	    int max = 10;
	    
	    assertEquals(computerDao.getCount(), computerDao.findAll().size());
	    assertEquals(max, computerDao.findAll(max, 0).size());
	}
    
    @Test
    public void computerCreateAndDelete() {
        Computer c = new Computer.Builder()
                                    .name("CPU")
                                    .manufacturer(new Company.Builder().id(1L).name("CPY").build()).build();
        Computer c2;
        
        assertEquals(0l, c.getId());
        c2 = computerDao.create(c);
        assertNotNull(c2.getId());
        assertNotNull(computerDao.findById(c2.getId()));
        computerDao.delete(c2.getId());
        assertNull(computerDao.findById(c2.getId()));
    }
    
    @Test
    public void computerUpdate() {
        Computer c = new Computer.Builder()
                                .name("CPU")
                                .manufacturer(new Company.Builder().id(1L).name("CPY").build()).build();
        
        c = computerDao.create(c);
        computerDao.update(new Computer.Builder()
                                        .id(c.getId())
                                        .name("CPU2")
                                        .manufacturer(new Company.Builder().id(2L).name("CPY2").build()).build());
        assertNotEquals(c, computerDao.findById(c.getId()));
        computerDao.delete(c.getId());
    }
}
