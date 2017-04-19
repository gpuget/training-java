package com.excilys.cdb.mappers;

import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.models.Company;

public final class CompanyMapper {
    /**
     * Converts a CompanyDTO to a Company.
     * @param companyDto DTO to convert
     * @return conversion result
     */
    public static Company toCompany(CompanyDTO companyDto) {
        return new Company.Builder(companyDto.getName()).id(
                Long.valueOf(companyDto.getId())).build();
    }

    /**
     * Converts a Company to a CompanyDTO.
     * @param company company to convert
     * @return conversion result
     */
    public static CompanyDTO toCompanyDTO(Company company) {
        CompanyDTO companyDto = new CompanyDTO();

        if (company != null) {
            companyDto.setName(company.getName());
            companyDto.setId(company.getId().toString());
        }

        return companyDto;
    }

    /**
     * Converts a list of Computer to a list of ComputerDTO.
     * @param computers list to convert
     * @return conversion result
     */
    public static List<CompanyDTO> toCompanyDTO(List<Company> computers) {
        List<CompanyDTO> listDto = new ArrayList<>();

        for (Company c : computers) {
            listDto.add(toCompanyDTO(c));
        }

        return listDto;
    }
}
