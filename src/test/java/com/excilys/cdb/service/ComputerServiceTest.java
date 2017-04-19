package com.excilys.cdb.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.models.Company;
import com.excilys.cdb.models.Computer;

public class ComputerServiceTest {
    private ComputerService computerService;
    
    @Before
    public void init(){
        this.computerService = new ComputerService();
    }
    
    @Test
    public void computerServiceGetPage(){
        assertNotNull(computerService.getPage());
        assertEquals(20, computerService.getPage().getObjects().size());
        assertEquals(ComputerDTO.class, computerService.getPage().getObjects().get(0).getClass());
    }
}
