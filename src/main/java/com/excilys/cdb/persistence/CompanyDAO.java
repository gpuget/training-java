package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.util.List;

import com.excilys.cdb.model.Company;

public interface CompanyDAO {
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
     *
     * @return found company
     */
    Company findById(long id);

    /**
     * Deletes the company corresponding to the identifier in DB.
     *
     * @param id identifier
     */
    void delete(long id);

    /**
     * Deletes the company corresponding to the identifier in DB.
     *
     * @param id identifier
     * @param connection provided connection
     */
    void delete(long id, Connection connection);

    /**
     * Inserts a company in DB.
     *
     * @param company company to insert
     * @return inserted company with its identifier
     */
    Company create(Company company);
}