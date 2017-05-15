package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public interface ComputerService {

    /**
     * Inserts the computer.
     *
     * @param computer computer to insert
     * @return inserted computer
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
    void deleteList(List<Long> idsList);

    /**
     * Updates the computer corresponding to the identifier after conversion.
     *
     * @param computer modified computer
     * @return modified computer
     */
    Computer update(Computer computer);

    /**
     * Gets the details of the computer corresponding to the identifier.
     *
     * @param id identifier
     * @return computer
     */
    Computer getDetails(long id);

    /**
     * Gets the total number of computers.
     *
     * @return total number of computers
     */
    int getCount();

    /**
     * Gets the page of computers.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @return page page of computers
     */
    Page<Computer> getPage(int number, int maxPerPage);

    /**
     * Gets the pages of computers corresponding to specified name.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @param name seek name
     * @return page of filtered computers
     */
    Page<Computer> getFilteredByNamePage(int number, int maxPerPage, String name);
}
