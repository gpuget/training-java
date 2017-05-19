package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;

public interface CompanyService {

    /**
     * Inserts a company in DB.
     *
     * @param company to insert
     * @return company with identifier
     */
    Company create(Company company);

    /**
     * Deletes the company corresponding to the identifier and its computers.
     *
     * @param id identifier
     */
    void delete(long id);

    /**
     * Returns all companies.
     *
     * @return found companies
     */
    List<Company> getCompanies();

    /**
     * Returns the Company corresponding to the identifier.
     *
     * @param id identifier
     * @return found company
     */
    Company getCompanyById(long id);
}
