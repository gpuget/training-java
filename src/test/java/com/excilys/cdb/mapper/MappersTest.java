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
        com = new Company.Builder("Bob Inc.").id(1L).build();
        cpu = new Computer.Builder("Bob").id(1L).introduced(LocalDate.MIN)
                .discontinued(LocalDate.MAX).manufacturer(com).build();
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
    public void modelToDTO() {
        ArrayList<Computer> cpuList = new ArrayList<>();
        ArrayList<ComputerDTO> cpuDtoList = new ArrayList<>();
        cpuList.add(cpu);
        cpuList.add(cpu);
        cpuDtoList.add(cpuDto);
        cpuDtoList.add(cpuDto);
        
        assertEquals(comDto, CompanyMapper.toCompanyDTO(com));
        assertEquals(cpuDto, ComputerMapper.toComputerDTO(cpu));
        assertEquals(cpuDtoList, ComputerMapper.toComputerDTO(cpuList));
    }

    @Test
    public void DTOToModel() {
        assertEquals(com, CompanyMapper.toCompany(comDto));
        assertEquals(cpu, ComputerMapper.toComputer(cpuDto));
    }
}
