package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.impl.CompanyDAOImpl;

public class CompanyService {
    private CompanyDAO companyDao;

    /**
     * Constructor.
     */
    public CompanyService() {
        this.companyDao = new CompanyDAOImpl();
    }

    public List<Company> getCompanies() {
        return companyDao.findAll();
    }
    
    public Company getCompanyById(long id) {
        return companyDao.findById(id);
    }
}
