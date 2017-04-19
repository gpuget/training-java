package com.excilys.cdb.mappers;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.models.Company;

public final class CompanyMapper {
    public static final Company toCompany(CompanyDTO companyDto){
        return new Company.Builder(companyDto.getName())
                            .id(Long.valueOf(companyDto.getId()))
                            .build();
    }
    
    public static final CompanyDTO toCompanyDTO(Company company){
        CompanyDTO companyDto = new CompanyDTO();
        
        if (company != null){
            companyDto.setName(company.getName());
            companyDto.setId(company.getId().toString());
        }
        
        return companyDto;
    }
}
