package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Company;

public interface CompanyDAO {

    /**
     * Inserts a company in DB.
     *
     * @param company company to insert
     * @return inserted company with its identifier
     */
    Company create(Company company);

    /**
     * Deletes the company corresponding to the identifier in DB.
     *
     * @param id identifier
     */
    void delete(long id);

    /**
     * Finds all companies in DB.
     *
     * @return list of all companies in DB
     */
    List<Company> findAll();

    /**
     * Finds the company corresponding to the identifier in DB.
     *
     * @param id identifier
     * @return found company
     */
    Company findById(long id);
    
    /**
     * Updates company in DB.
     *
     * @param company modified company
     * @return company
     */
    Company update(Company company);
}