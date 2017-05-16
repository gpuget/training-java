package com.excilys.cdb.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.ComputerService;

@Service("computerService")
public class ComputerServiceImpl implements ComputerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerServiceImpl.class);

    @Autowired
    private ComputerDAO computerDao;
    private int count;

    /**
     * Bean initialization.
     */
    @PostConstruct
    private void init() {
        this.count = computerDao.getCount();
    }

    /**
     * Inserts the computer.
     *
     * @param computer computer to insert
     * @return inserted computer
     */
    public Computer create(Computer computer) {
        LOGGER.info("Create computer : " + computer);
        ++count;
        LOGGER.debug("Count : " + count);
        try {
            return computerDao.create(computer);
        } catch (DAOException e) {
            --count;
            LOGGER.debug("Count : " + count);
            throw e;
        }
    }

    /**
     * Deletes the computer corresponding to the identifier.
     *
     * @param id identifier
     */
    public void delete(long id) {
        LOGGER.info("Delete computer by id : " + id);
        computerDao.delete(id);
        --count;
    }

    /**
     * Deletes the computers corresponding to the identifiers.
     *
     * @param idsList identifiers
     */
    public void deleteList(List<Long> idsList) {
        LOGGER.info("Delete computers by ids : " + idsList);
        computerDao.delete(idsList);
        count -= idsList.size();
    }

    /**
     * Updates the computer corresponding to the identifier after conversion.
     *
     * @param computer modified computer
     * @return modified computer
     */
    public Computer update(Computer computer) {
        LOGGER.info("Update computer : " + computer);
        return computerDao.update(computer);
    }

    /**
     * Gets the details of the computer corresponding to the identifier.
     *
     * @param id identifier
     * @return computer
     */
    public Computer getDetails(long id) {
        LOGGER.info("Get computer details with id : " + id);
        return computerDao.findById(id);
    }

    /**
     * Gets the total number of computers.
     *
     * @return total number of computers
     */
    public int getCount() {
        LOGGER.info("Count of computers : " + count);
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
        LOGGER.info("Get page of computer : number " + number + " with " + maxPerPage + " computers");
        return new Page<>(number, computerDao.findAll(maxPerPage, maxPerPage * (number - 1)));
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
        LOGGER.info("Get page of filtered computer : (" + name + ") number " + number + "with "
                + maxPerPage + "computers");
        return new Page<>(number,
                computerDao.getFilteredByName(maxPerPage, maxPerPage * (number - 1), name));
    }

    /**
     * Sets the computer DAO.
     *
     * @param computerDao computer DAO to use
     */
    public void setComputerDao(ComputerDAO computerDao) {
        LOGGER.info("Set computer DAO");
        this.computerDao = computerDao;
    }
}