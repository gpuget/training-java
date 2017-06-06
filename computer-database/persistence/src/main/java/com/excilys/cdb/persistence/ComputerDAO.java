package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Computer;

public interface ComputerDAO extends DAO<Computer, Long>{
    /**
     * Deletes the computers corresponding to the identifiers.
     *
     * @param idsList identifiers
     */
    void delete(List<Long> idsList);

    /**
     * Deletes the computers from company corresponding to the identifier.
     *
     * @param companyId company identifier
     */
    void deleteFromCompany(long companyId);

    /**
     * Finds all computers in an interval fixed by limit and offset parameters.
     *
     * @param limit limit size of the list returned
     * @param offset start of the search
     * @param column column for orderby
     * @param order ASC (1) or DESC (0)
     * @return list of all computers in the interval
     */
    List<Computer> findAll(int limit, int offset, String column, int order);

    /**
     * Finds all computers in an interval fixed by limit and offset parameters and filtered by name.
     *
     * @param limit limit size of the list returned
     * @param offset start of the search
     * @param column column for orderby
     * @param order ASC (1) or DESC (0)
     * @param name seek name
     * @return list of computers
     */
    List<Computer> findByName(int limit, int offset, String column, int order, String name);

    /**
     * Gets the total number of computers.
     *
     * @return total number of computers
     */
    int getCount();

    /**
     * Gets the total number of computers with name like.
     *
     * @param name seek name
     * @return total number of computers
     */
    int getCount(String name);
}