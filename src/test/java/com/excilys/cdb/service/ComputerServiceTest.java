package com.excilys.cdb.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.cdb.service.ComputerService;

public class ComputerServiceTest {
    @Test
    public void correctPage(){
        int max = 10;
        
        assertEquals(max, ComputerService.INSTANCE.getPage(1, max).getObjects().size());
    }
}
