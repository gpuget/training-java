package com.excilys.cdb.service;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.models.Page;
import com.excilys.cdb.persistences.CompanyDAO;
import com.excilys.cdb.persistences.CompanyDAOImpl;

public class CompanyService {
    private CompanyDAO companyDao;
    private Page<CompanyDTO> page;
    
    public CompanyService(){
        this.companyDao = new CompanyDAOImpl();
        this.page = new Page<>(CompanyMapper.toCompanyDTO(companyDao.findAll()));
    }
    
    public Page<CompanyDTO> getPage(){
        return this.page;
    }
}
