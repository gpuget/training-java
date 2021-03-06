package com.excilys.cdb.mapper.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.dto.CompanyDTO;

@Component
public class CompanyMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyMapper.class);

    /**
     * Converts a CompanyDTO to a Company.
     *
     * @param companyDto DTO to convert
     * @return conversion result
     */
    public Company toCompany(CompanyDTO companyDto) {
        LOGGER.info("Convert CompanyDTO to model : " + companyDto);
        return new Company.Builder().id(companyDto.getId()).name(companyDto.getName()).build();
    }

    /**
     * Converts a Company to a CompanyDTO.
     *
     * @param company company to convert
     * @return conversion result
     */
    public CompanyDTO toCompanyDTO(Company company) {
        LOGGER.info("Convert Company to DTO : " + company);
        CompanyDTO companyDto = new CompanyDTO();

        if (company != null) {
            companyDto.setId(company.getId());
            companyDto.setName(company.getName());
        }

        return companyDto;
    }

    /**
     * Converts a list of Company to a list of CompanyDTO.
     *
     * @param companies list to convert
     * @return conversion result
     */
    public List<CompanyDTO> toCompanyDTO(List<Company> companies) {
        LOGGER.info("Convert Companies to DTO");
        List<CompanyDTO> listDto = new ArrayList<>();

        for (Company c : companies) {
            listDto.add(toCompanyDTO(c));
        }

        return listDto;
    }
}
