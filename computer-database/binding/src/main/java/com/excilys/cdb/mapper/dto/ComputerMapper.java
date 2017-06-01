package com.excilys.cdb.mapper.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.dto.ComputerDTO;

@Component
public class ComputerMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);

    /**
     * Converts a ComputerDTO to a Computer.
     *
     * @param computerDto DTO to convert
     * @return conversion result
     */
    public Computer toComputer(ComputerDTO computerDto) {
        LOGGER.info("Computer DTO to model : " + computerDto);
        Computer computer = new Computer.Builder().build();

        if (computerDto != null) {
            computer.setId(computerDto.getId());
            computer.setName(computerDto.getName());
            computer.setManufacturer(new Company.Builder().id(computerDto.getCompanyId())
                    .name(computerDto.getCompanyName()).build());

            String i = computerDto.getIntroduced();
            String d = computerDto.getDiscontinued();
            computer.setIntroduced((i != null && !i.isEmpty()
                    ? LocalDate.parse(i, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null));
            computer.setDiscontinued((d != null && !d.isEmpty()
                    ? LocalDate.parse(d, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null));

        }

        return computer;
    }

    /**
     * Converts a Computer to a ComputerDTO.
     *
     * @param computer computer to convert
     * @return conversion result
     */
    public ComputerDTO toComputerDTO(Computer computer) {
        LOGGER.info("Computer model to DTO : " + computer);
        ComputerDTO computerDto = new ComputerDTO();

        if (computer != null) {
            Company company = computer.getManufacturer();

            computerDto.setId(computer.getId());
            computerDto.setName(computer.getName());
            computerDto.setIntroduced(String.valueOf(computer.getIntroduced()));
            computerDto.setDiscontinued(String.valueOf(computer.getDiscontinued()));

            if (company != null) {
                computerDto.setCompanyId(company.getId());
                computerDto.setCompanyName(company.getName());
            } else {
                computerDto.setCompanyName("");
            }
        }

        return computerDto;
    }

    /**
     * Converts a list of Computer to a list of ComputerDTO.
     *
     * @param computers list to convert
     * @return conversion result
     */
    public List<ComputerDTO> toComputerDTO(List<Computer> computers) {
        LOGGER.info("Computer list model to DTO");
        List<ComputerDTO> listDto = new ArrayList<>();

        for (Computer c : computers) {
            listDto.add(toComputerDTO(c));
        }

        return listDto;
    }
}