package com.excilys.cdb.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void deleteList(List<Long> idsList) {
        LOGGER.info("Delete computers by ids : " + idsList);
        computerDao.delete(idsList);
        count -= idsList.size();
    }

    @Override
    public int getCount() {
        LOGGER.info("Count of computers : " + count);
        return count;
    }

    @Override
    public ComputerDTO getDetails(long id) {
        LOGGER.info("Get computer details with id : " + id);
        return ComputerMapper.toComputerDTO(computerDao.findById(id));
    }

    @Override
    public Page<ComputerDTO> getFilteredByNamePage(int number, int maxPerPage, String name) {
        LOGGER.info("Get page of filtered computer : (" + name + ") number " + number + " with "
                + maxPerPage + " computers");
        List<Computer> computers = computerDao.findByName(maxPerPage, maxPerPage * (number - 1),
                name);
        return new Page<>(number, ComputerMapper.toComputerDTO(computers));
    }

    @Override
    public Page<ComputerDTO> getPage(int number, int maxPerPage) {
        LOGGER.info(
                "Get page of computer : number " + number + " with " + maxPerPage + " computers");
        List<Computer> computers = computerDao.findAll(maxPerPage, maxPerPage * (number - 1));
        return new Page<>(number, ComputerMapper.toComputerDTO(computers));
    }

    @Override
    @Transactional
    public ComputerDTO update(ComputerDTO computerDto) {
        LOGGER.info("Update computer : " + computerDto);
        Computer computer = computerDao.update(ComputerMapper.toComputer(computerDto));
        return ComputerMapper.toComputerDTO(computer);
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