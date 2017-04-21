package com.excilys.cdb.services;

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

    public Page<Company> getPage() {
        return new Page<>(companyDao.findAll());
    }
}
