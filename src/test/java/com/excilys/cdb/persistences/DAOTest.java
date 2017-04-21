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
        
        assertTrue(Connector.INSTANCE.isDisconnected());
        assertEquals(0l, c.getId());
        c2 = computerDao.create(c);
        assertNotNull(c2.getId());
        assertNotNull(computerDao.findById(c2.getId()));
        computerDao.delete(c2.getId());
        assertNull(computerDao.findById(c2.getId()));
        assertTrue(Connector.INSTANCE.isDisconnected());
    }
    
    @Test
    public void computerUpdate() {
        Computer c = new Computer.Builder()
                                .name("CPU")
                                .manufacturer(new Company.Builder().id(1L).name("CPY").build()).build();
        
        assertTrue(Connector.INSTANCE.isDisconnected());
        c = computerDao.create(c);
        computerDao.update(new Computer.Builder()
                                        .id(c.getId())
                                        .name("CPU2")
                                        .manufacturer(new Company.Builder().id(2L).name("CPY2").build()).build());
        assertNotEquals(c, computerDao.findById(c.getId()));
        computerDao.delete(c.getId());
        assertTrue(Connector.INSTANCE.isDisconnected());
    }
}
