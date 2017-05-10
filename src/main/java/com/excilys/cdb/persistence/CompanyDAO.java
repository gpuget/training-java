package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Company;

public interface CompanyDAO {
    /**
     * Finds all companies in DB.
     * @return list of all companies in DB
     */
    List<Company> findAll();

    /**
     * Finds the company corresponding to the identifier in DB.
     * @return found company
     */
    Company findById(long id);
}
