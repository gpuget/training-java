package com.excilys.cdb.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.models.Company;
import com.excilys.cdb.models.Computer;

public class MappersTest {
    Company com;
    Computer cpu;
    CompanyDTO comDto;
    ComputerDTO cpuDto;

    @Before
    public void init() {
        com = new Company.Builder().id(1L).name("Bob Inc.").build();
        cpu = new Computer.Builder()
                            .id(1L)
                            .name("Bob")
                            .introduced(LocalDate.MIN)
                            .discontinued(LocalDate.MAX)
                            .manufacturer(com).build();
        
        comDto = new CompanyDTO();
        comDto.setName("Bob Inc.");
        comDto.setId("1");
        
        cpuDto = new ComputerDTO();
        cpuDto.setName("Bob");
        cpuDto.setId("1");
        cpuDto.setIntroduced(LocalDate.MIN.toString());
        cpuDto.setDiscontinued(LocalDate.MAX.toString());
        cpuDto.setManufacturer(comDto);
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
