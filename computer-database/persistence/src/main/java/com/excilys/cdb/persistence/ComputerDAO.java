package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Computer;

public interface ComputerDAO {
    /**
     * Inserts a computer in DB.
     *
     * @param computer computer to insert
     * @return inserted computer with its identifier
     */
    Computer create(Computer computer);

    /**
     * Deletes the computer corresponding to the identifier.
     *
     * @param id identifier
     */
    void delete(long id);

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
     * Finds all computers in DB.
     *
     * @return list of all computers
     */
    List<Computer> findAll();

    /**
     * Finds all computers in an interval fixed by limit and offset parameters.
     *
     * @param limit limit size of the list returned
     * @param offset start of the search
     * @return list of all computers in the interval
     */
    List<Computer> findAll(int limit, int offset);

    /**
     * Finds the computer corresponding to the identifier.
     *
     * @param id identifier
     * @return found computer
     */
    Computer findById(long id);

    /**
     * Finds all computers in an interval fixed by limit and offset parameters and filtered by name.
     *
     * @param limit limit size of the list returned
     * @param offset start of the search
     * @param name seek name
     * @return list of computers
     */
    List<Computer> findByName(int limit, int offset, String name);

    /**
     * Gets the total number of computers.
     *
     * @return total number of computers
     */
    int getCount();

    /**
     * Updates the computer corresponding to the identifier.
     *
     * @param computer modified computer
     * @return modified computer
     */
    Computer update(Computer computer);
}