package com.excilys.cdb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.model.dto.ComputerDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { com.excilys.cdb.config.AppConfig.class })
public class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComputerService computerService;

    @Test
    public void createAndDelete() {
        CompanyDTO comDto = new CompanyDTO(0L, "Bob Inc.");
        
        comDto = companyService.create(comDto);
        assertNotEquals(0L, comDto.getId());
        companyService.delete(comDto.getId());
    }

    @Test
    public void deleteWithComputer() {
        CompanyDTO comDto = new CompanyDTO(0L, "Bob Inc.");

        comDto = companyService.create(comDto);
        ComputerDTO cpuDto = new ComputerDTO();
        cpuDto.setName("Bob");
        cpuDto.setCompanyId(comDto.getId());
        cpuDto = computerService.create(cpuDto);
        assertNotNull(computerService.getDetails(cpuDto.getId()));
        companyService.delete(comDto.getId());
        assertEquals(new ComputerDTO(), computerService.getDetails(cpuDto.getId()));
        assertEquals(new CompanyDTO(), companyService.getCompanyById(comDto.getId()));
    }
}
