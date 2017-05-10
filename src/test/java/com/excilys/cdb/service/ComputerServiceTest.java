package com.excilys.cdb.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.service.ComputerService;

public class ComputerServiceTest {
    private ComputerService computerService;
    
    @Before
    public void init(){
        this.computerService = new ComputerService();
    }

    @Test
    public void correctPage(){
        int max = 10;
        
        assertEquals(max, computerService.getPage(1, max).getObjects().size());
    }
}
