package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.impl.ComputerDAOImpl;

public enum ComputerService {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    private int count;

    /**
     * Constructor.
     */
    ComputerService() {
        this.count = ComputerDAOImpl.INSTANCE.getCount();
    }

    /**
     * Inserts the computer.
     *
     * @param computer computer to insert
     * @return inserted computer
     */
    public Computer create(Computer computer) {
        LOGGER.trace("Create computer : " + computer);
        count++;
        try {
            return ComputerDAOImpl.INSTANCE.create(computer);
        } catch (Exception e) {
            count--;
            throw e;
        }
    }

    /**
     * Deletes the computer corresponding to the identifier.
     *
     * @param id identifier
     */
    public void delete(long id) {
        LOGGER.trace("Delete computer by id : " + id);
        ComputerDAOImpl.INSTANCE.delete(id);
        count--;
    }

    /**
     * Deletes the computers corresponding to the identifiers.
     *
     * @param idsList identifiers
     */
    public void deleteList(List<Long> idsList) {
        LOGGER.trace("Delete computers by ids : " + idsList);
        ComputerDAOImpl.INSTANCE.delete(idsList);
        count -= idsList.size();
    }

    /**
     * Updates the computer corresponding to the identifier after conversion.
     *
     * @param computer modified computer
     * @return modified computer
     */
    public Computer update(Computer computer) {
        LOGGER.trace("Update computer : " + computer);
        return ComputerDAOImpl.INSTANCE.update(computer);
    }

    /**
     * Gets the details of the computer corresponding to the identifier.
     *
     * @param id identifier
     * @return computer
     */
    public Computer getDetails(long id) {
        LOGGER.trace("Get computer details with id : " + id);
        return ComputerDAOImpl.INSTANCE.findById(id);
    }

    /**
     * Gets the total number of computers.
     *
     * @return total number of computers
     */
    public int getCount() {
        LOGGER.trace("Count of computers : " + count);
        return count;
    }

    /**
     * Gets the page of computers.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @return page page of computers
     */
    public Page<Computer> getPage(int number, int maxPerPage) {
        LOGGER.trace("Get page of computer : number " + number + "with " + maxPerPage + "computers");
        return new Page<>(number,
                ComputerDAOImpl.INSTANCE.findAll(maxPerPage, maxPerPage * (number - 1)));
    }

    /**
     * Gets the pages of computers corresponding to specified name.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @param name seek name
     * @return page of filtered computers
     */
    public Page<Computer> getFilteredByNamePage(int number, int maxPerPage, String name) {
        LOGGER.trace("Get page of filtered computer : (" + name + ") number " + number + "with " + maxPerPage + "computers");
        return new Page<>(number, ComputerDAOImpl.INSTANCE.getFilteredByName(maxPerPage,
                maxPerPage * (number - 1), name));
    }
}