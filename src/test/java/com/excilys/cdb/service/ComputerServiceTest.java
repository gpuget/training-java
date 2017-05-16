package com.excilys.cdb.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={com.excilys.cdb.config.SpringConfig.class})
public class ComputerServiceTest {
    @Autowired
    private ComputerService computerService;
    
    @Test
    public void correctPage(){        
        assertEquals(10, computerService.getPage(1, 10).getObjects().size());
        assertEquals(computerService.getDetails(12L), computerService.getPage(2, 10).getObjects().get(1));
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
