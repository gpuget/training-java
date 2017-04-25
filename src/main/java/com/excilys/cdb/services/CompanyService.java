package com.excilys.cdb.services;

import java.util.List;

import com.excilys.cdb.models.Company;
import com.excilys.cdb.models.Page;
import com.excilys.cdb.persistences.CompanyDAO;
import com.excilys.cdb.persistences.CompanyDAOImpl;

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
