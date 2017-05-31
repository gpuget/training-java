package com.excilys.cdb.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ComputerMapper computerMapper;

    /**
     * Bean initialization.
     */
    @PostConstruct
    private void init() {
        LOGGER.info("Initialization computer service...");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ComputerDTO create(ComputerDTO computerDto) {
        LOGGER.info("Create computer : " + computerDto);
        Computer computer = computerDao.create(computerMapper.toComputer(computerDto));
        return computerMapper.toComputerDTO(computer);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteList(List<Long> idsList) {
        LOGGER.info("Delete computers by ids : " + idsList);
        computerDao.delete(idsList);
    }

    @Override
    public ComputerDTO getDetails(long id) {
        LOGGER.info("Get computer details with id : " + id);
        return computerMapper.toComputerDTO(computerDao.find(id));
    }

    @Override
    public Page<ComputerDTO> getFilteredByNamePage(int number, int maxPerPage, String name) {
        LOGGER.info("Get page of filtered computer : (" + name + ") number " + number + " with "
                + maxPerPage + " computers");
        List<Computer> computers = computerDao.findByName(maxPerPage, maxPerPage * (number - 1),
                name);
        int count = computerDao.getCount(name);
        Page<ComputerDTO> page = new Page<>(number, maxPerPage, computerMapper.toComputerDTO(computers));
        page.setTotal(count);

        return page;
    }

    @Override
    public Page<ComputerDTO> getPage(int number, int maxPerPage) {
        LOGGER.info(
                "Get page of computer : number " + number + " with " + maxPerPage + " computers");
        List<Computer> computers = computerDao.findAll(maxPerPage, maxPerPage * (number - 1));
        int count = computerDao.getCount();
        Page<ComputerDTO> page = new Page<>(number, maxPerPage, computerMapper.toComputerDTO(computers));
        page.setTotal(count);

        return page;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ComputerDTO update(ComputerDTO computerDto) {
        LOGGER.info("Update computer : " + computerDto);
        Computer computer = computerDao.update(computerMapper.toComputer(computerDto));
        return computerMapper.toComputerDTO(computer);
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