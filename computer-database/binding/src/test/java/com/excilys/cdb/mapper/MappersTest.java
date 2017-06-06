package com.excilys.cdb.mapper;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    CompanyMapper companyMapper;
    ComputerMapper computerMapper;

    @Before
    public void init() {
        companyMapper = new CompanyMapper();
        computerMapper = new ComputerMapper();
        
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
    public void goodConversionToCompany(){
        assertEquals(com, companyMapper.toCompany(comDto));
    }

    @Test
    public void goodConversionToCompanyDTO(){
        assertEquals(comDto, companyMapper.toCompanyDTO(com));
    }
    
    @Test
    public void goodListConversionToCompanyDTO() {
        List<Company> companies = new ArrayList<>();
        List<CompanyDTO> companiesDto = new ArrayList<>();
        
        companies.add(com);
        companies.add(com);
        companiesDto.add(comDto);
        companiesDto.add(comDto);
        
        assertEquals(companiesDto, companyMapper.toCompanyDTO(companies));
    }

    @Test
    public void goodConversionToComputer(){
        assertEquals(cpu, computerMapper.toComputer(cpuDto));
    }

    @Test
    public void goodConversionToComputerDTO(){
        assertEquals(cpuDto, computerMapper.toComputerDTO(cpu));
    }
    
    @Test
    public void goodListConversionToComputerDTO() {
        List<Computer> computers = new ArrayList<>();
        List<ComputerDTO> computersDto = new ArrayList<>();
        
        computers.add(cpu);
        computers.add(cpu);
        computersDto.add(cpuDto);
        computersDto.add(cpuDto);
        
        assertEquals(computersDto, computerMapper.toComputerDTO(computers));
    }
}
