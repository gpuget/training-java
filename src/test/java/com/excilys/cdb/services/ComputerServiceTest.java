package com.excilys.cdb.services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.services.ComputerService;

public class ComputerServiceTest {
    private ComputerService computerService;
    
    @Before
    public void init(){
        this.computerService = new ComputerService();
    }
    
    @Test
    public void computerServiceGetPage(){
        assertNotNull(computerService.getPage());
        assertFalse(computerService.getPage().getObjects().isEmpty());
        assertEquals(ComputerService.MAX_PER_PAGE, computerService.getPage().maxPerPage);
        assertEquals(ComputerService.MAX_PER_PAGE, computerService.getPage().getObjects().size());
    }
}
