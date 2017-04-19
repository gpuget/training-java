package com.excilys.cdb.mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.models.Computer;

public final class ComputerMapper {    
    public static final Computer toComputer(ComputerDTO computerDto){
        return new Computer.Builder(computerDto.getName())
                            .id(Long.valueOf(computerDto.getId()))
                            .introduced(LocalDate.parse(computerDto.getIntroduced()))
                            .discontinued(LocalDate.parse(computerDto.getDiscontinued()))
                            .manufacturer(CompanyMapper.toCompany(computerDto.getManufacturer()))
                            .build();
                
    }
    
    public static final ComputerDTO toComputerDTO(Computer computer){
        ComputerDTO computerDto = new ComputerDTO();
        
        if (computer != null){
            computerDto.setName(computer.getName());
            computerDto.setId(computer.getId().toString());
            computerDto.setIntroduced(String.valueOf(computer.getIntroduced()));
            computerDto.setDiscontinued(String.valueOf(computer.getDiscontinued()));
            computerDto.setManufacturer(CompanyMapper.toCompanyDTO(computer.getManufacturer()));
        }
        
        return computerDto;
    }
    
    public static final List<ComputerDTO> toComputerDTO(List<Computer> computers){
        List<ComputerDTO> listDto = new ArrayList<>();
        
        for(Computer c : computers){
            listDto.add(toComputerDTO(c));
        }
        
        return listDto;
    }
}
