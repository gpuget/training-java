package com.excilys.cdb.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.mapper.dto.CompanyMapper;
import com.excilys.cdb.mapper.dto.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.model.dto.ComputerDTO;

public class MappersTest {
    Company com;
    Computer cpu;
    CompanyDTO comDto;
    ComputerDTO cpuDto;

    @Before
    public void init() {
        com = new Company.Builder()
                            .id(1L)
                            .name("Bob Inc.")
                            .build();
        cpu = new Computer.Builder()
                            .id(1L)
                            .name("Bob")
                            .introduced(LocalDate.of(1970, 01, 01))
                            .discontinued(LocalDate.of(2017, 01, 01))
                            .manufacturer(com)
                            .build();
        
        comDto = new CompanyDTO(1L, "Bob Inc.");
        
        cpuDto = new ComputerDTO();
        cpuDto.setName("Bob");
        cpuDto.setId(1L);
        cpuDto.setIntroduced(LocalDate.of(1970, 01, 01).toString());
        cpuDto.setDiscontinued(LocalDate.of(2017, 01, 01).toString());
        cpuDto.setCompanyId(1L);
        cpuDto.setCompanyName("Bob Inc.");
    }

    @Test
    public void goodConversionToCompanyDTO(){
        assertEquals(comDto, CompanyMapper.toCompanyDTO(com));
    }

    @Test
    public void goodConversionToCompany(){
        assertEquals(com, CompanyMapper.toCompany(comDto));
    }

    @Test
    public void goodConversionToComputerDTO(){
        assertEquals(cpuDto, ComputerMapper.toComputerDTO(cpu));
    }

    @Test
    public void goodConversionToComputer(){
        assertEquals(cpu, ComputerMapper.toComputer(cpuDto));
    }
}
