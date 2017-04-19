package com.excilys.cdb.mappers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.models.Computer;

public final class ComputerMapper {    
    public static final Computer toComputer(ComputerDTO computerDto){
        return new Computer.Builder(computerDto.getName())
                            .id(Long.valueOf(computerDto.getId()))
                            .introduced(Timestamp.valueOf(computerDto.getIntroduced()).toLocalDateTime().toLocalDate())
                            .discontinued(Timestamp.valueOf(computerDto.getDiscontinued()).toLocalDateTime().toLocalDate())
                            .manufacturer(CompanyMapper.toCompany(computerDto.getManufacturer()))
                            .build();
                
    }
    
    public static final ComputerDTO toComputerDTO(Computer computer){
        ComputerDTO computerDto = new ComputerDTO();
        
        if (computer != null){
            computerDto.setName(computer.getName());
            computerDto.setId(computer.getId().toString());
            computerDto.setIntroduced(computer.getIntroduced().toString());
            computerDto.setDiscontinued(computer.getDiscontinued().toString());
            computerDto.setName(computer.getName());
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
