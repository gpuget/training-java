package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.dto.CompanyDTO;

public interface CompanyService {

    /**
     * Inserts a company in DB.
     *
     * @param companyDto to insert
     * @return company with identifier
     */
    CompanyDTO create(CompanyDTO companyDto);

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
    List<CompanyDTO> getCompanies();

    /**
     * Returns the Company corresponding to the identifier.
     *
     * @param id identifier
     * @return found company
     */
    CompanyDTO getCompanyById(long id);

    /**
     * Updates the company corresponding to the identifier after conversion.
     *
     * @param companyDto modified company
     * @return modified company
     */
    CompanyDTO update(CompanyDTO companyDto);
}
