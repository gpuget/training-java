package com.excilys.cdb.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.mapper.dto.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.dto.ComputerDTO;
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
        LOGGER.info("Initialization computer service...");
        this.count = computerDao.getCount();
    }

    /**
     * Inserts the computer.
     *
     * @param computer computer to insert
     * @return inserted computer
     */
    public ComputerDTO create(ComputerDTO computerDto) {
        LOGGER.info("Create computer : " + computerDto);
        ++count;
        LOGGER.debug("Count : " + count);
        try {
            Computer computer = computerDao.create(ComputerMapper.toComputer(computerDto));
            return ComputerMapper.toComputerDTO(computer);
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
     * Gets the total number of computers.
     *
     * @return total number of computers
     */
    public int getCount() {
        LOGGER.info("Count of computers : " + count);
        return count;
    }

    /**
     * Gets the details of the computer corresponding to the identifier.
     *
     * @param id identifier
     * @return computer
     */
    public ComputerDTO getDetails(long id) {
        LOGGER.info("Get computer details with id : " + id);
        return ComputerMapper.toComputerDTO(computerDao.findById(id));
    }

    /**
     * Gets the pages of computers corresponding to specified name.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @param name seek name
     * @return page of filtered computers
     */
    public Page<ComputerDTO> getFilteredByNamePage(int number, int maxPerPage, String name) {
        LOGGER.info("Get page of filtered computer : (" + name + ") number " + number + " with "
                + maxPerPage + " computers");
        List<Computer> computers = computerDao.findByName(maxPerPage, maxPerPage * (number - 1), name);
        return new Page<>(number, ComputerMapper.toComputerDTO(computers));
    }

    /**
     * Gets the page of computers.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @return page page of computers
     */
    public Page<ComputerDTO> getPage(int number, int maxPerPage) {
        LOGGER.info(
                "Get page of computer : number " + number + " with " + maxPerPage + " computers");
        List<Computer> computers = computerDao.findAll(maxPerPage, maxPerPage * (number - 1));
        return new Page<>(number, ComputerMapper.toComputerDTO(computers));
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

    /**
     * Updates the computer corresponding to the identifier after conversion.
     *
     * @param computer modified computer
     * @return modified computer
     */
    public ComputerDTO update(ComputerDTO computerDto) {
        LOGGER.info("Update computer : " + computerDto);
        Computer computer = computerDao.update(ComputerMapper.toComputer(computerDto));
        return ComputerMapper.toComputerDTO(computer);
    }
}