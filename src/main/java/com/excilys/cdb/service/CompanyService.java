package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.impl.CompanyDAOImpl;

public enum CompanyService {
	INSTANCE;

    public List<Company> getCompanies() {
        return CompanyDAOImpl.INSTANCE.findAll();
    }
    
    public Company getCompanyById(long id) {
        return CompanyDAOImpl.INSTANCE.findById(id);
    }
}
