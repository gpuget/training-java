package com.excilys.cdb.mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.models.Computer;

public final class ComputerMapper {
    /**
     * Converts a ComputerDTO to a Computer.
     * @param computerDto DTO to convert
     * @return conversion result
     */
    public static Computer toComputer(ComputerDTO computerDto) {
        return new Computer.Builder(computerDto.getName())
                            .id(Long.valueOf(computerDto.getId()))
                            .introduced(LocalDate.parse(computerDto.getIntroduced()))
                            .discontinued(LocalDate.parse(computerDto.getDiscontinued()))
                            .manufacturer(CompanyMapper.toCompany(computerDto.getManufacturer()))
                            .build();
    }

    /**
     * Converts a Computer to a ComputerDTO.
     * @param computer computer to convert
     * @return conversion result
     */
    public static ComputerDTO toComputerDTO(Computer computer) {
        ComputerDTO computerDto = new ComputerDTO();

        if (computer != null) {
            computerDto.setName(computer.getName());
            computerDto.setId(String.valueOf(computer.getId()));
            computerDto.setIntroduced(String.valueOf(computer.getIntroduced()));
            computerDto.setDiscontinued(String.valueOf(computer.getDiscontinued()));
            computerDto.setManufacturer(CompanyMapper.toCompanyDTO(computer.getManufacturer()));
        }

        return computerDto;
    }

    /**
     * Converts a list of Computer to a list of ComputerDTO.
     * @param computers list to convert
     * @return conversion result
     */
    public static List<ComputerDTO> toComputerDTO(List<Computer> computers) {
        List<ComputerDTO> listDto = new ArrayList<>();

        for (Computer c : computers) {
            listDto.add(toComputerDTO(c));
        }

        return listDto;
    }
}
