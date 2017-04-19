package com.excilys.cdb.service;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.models.Computer;
import com.excilys.cdb.models.Page;
import com.excilys.cdb.persistences.ComputerDAO;
import com.excilys.cdb.persistences.ComputerDAOImpl;

public class ComputerService {
    private ComputerDAO computerDao;
    private Page<ComputerDTO> page;

    public ComputerService() {
        this.computerDao = new ComputerDAOImpl();
    }

    public Computer create(ComputerDTO computerDto) {
        return computerDao.create(ComputerMapper.toComputer(computerDto));
    }

    public void delete(String id) {
        computerDao.delete(Long.valueOf(id));
    }

    public void update(ComputerDTO computerDto) {
        computerDao.update(ComputerMapper.toComputer(computerDto));
    }

    public Page<ComputerDTO> getPage() {
        return this.page;
    }
}