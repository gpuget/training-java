package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.dto.ComputerDTO;

public final class ComputerMapper {
    /**
     * Converts a ComputerDTO to a Computer.
     *
     * @param computerDto DTO to convert
     * @return conversion result
     */
    public static Computer toComputer(ComputerDTO computerDto) {
        return new Computer.Builder().id(Long.valueOf(computerDto.getId()))
                .name(computerDto.getName())
                .introduced(LocalDate.parse(computerDto.getIntroduced()))
                .discontinued(LocalDate.parse(computerDto.getDiscontinued()))
                .manufacturer(CompanyMapper.toCompany(computerDto.getManufacturer())).build();
    }

    /**
     * Converts a Computer to a ComputerDTO.
     *
     * @param computer computer to convert
     * @return conversion result
     */
    public static ComputerDTO toComputerDTO(Computer computer) {
        ComputerDTO computerDto = new ComputerDTO();

        computerDto.setId(String.valueOf(computer.getId()));
        computerDto.setName(computer.getName());
        computerDto.setIntroduced(String.valueOf(computer.getIntroduced()));
        computerDto.setDiscontinued(String.valueOf(computer.getDiscontinued()));
        computerDto.setManufacturer(CompanyMapper.toCompanyDTO(computer.getManufacturer()));

        return computerDto;
    }

    /**
     * Converts a list of Computer to a list of ComputerDTO.
     *
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

    /**
     * Converts a page of Computer to a page of ComputerDTO.
     *
     * @param page page to convert
     *
     * @return conversion result
     */
    public static Page<ComputerDTO> toComputerDTO(Page<Computer> page) {
        return new Page<>(page.getNumber(), toComputerDTO(page.getObjects()));
    }
}