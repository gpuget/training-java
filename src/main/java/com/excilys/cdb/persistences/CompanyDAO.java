package com.excilys.cdb.persistences;

import java.util.List;

import com.excilys.cdb.models.Company;

public interface CompanyDAO {
    /**
     * Finds all companies in DB.
     * @return list of all companies in DB
     */
    List<Company> findAll();
}
