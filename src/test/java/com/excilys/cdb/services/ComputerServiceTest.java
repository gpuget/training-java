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
        assertEquals(computerService.getMaxPerPage(), computerService.getPage().maxPerPage);
        assertEquals(computerService.getMaxPerPage(), computerService.getPage().getObjects().size());
        System.out.println(computerService.getPage().getObjects().get(0));
    }
    
    @Test
    public void computerServiceTotal(){
        assertNotEquals(0, computerService.getTotal());
    }
}
