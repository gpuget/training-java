package com.excilys.cdb.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.dto.ComputerDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { com.excilys.cdb.config.AppConfig.class })
public class ComputerServiceTest {
    @Autowired
    private ComputerService computerService;

    @Test
    public void correctPage() {
        assertEquals(10, computerService.getPage(1, 10).getObjects().size());
        assertEquals(computerService.getDetails(12L),
                computerService.getPage(2, 10).getObjects().get(1));
    }

    @Test
    public void createAndDelete() {
        ComputerDTO cpuDto = new ComputerDTO();
        cpuDto.setName("Bob");
        cpuDto.setCompanyId(1L);

        cpuDto = computerService.create(cpuDto);
        computerService.delete(cpuDto.getId());
    }
}
