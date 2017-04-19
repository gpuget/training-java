package com.excilys.cdb.service;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.models.Computer;
import com.excilys.cdb.models.Page;
import com.excilys.cdb.persistences.ComputerDAO;
import com.excilys.cdb.persistences.ComputerDAOImpl;

public class ComputerService {
    public static final int MAX_PER_PAGE = 10;
    
    private ComputerDAO computerDao;
    private Page<ComputerDTO> page;

    public ComputerService() {
        this.computerDao = new ComputerDAOImpl();
        this.page = new Page<>(ComputerMapper.toComputerDTO(computerDao.findAll(MAX_PER_PAGE, 0)));
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
    
    public ComputerDTO getDetails(String id){
        return ComputerMapper.toComputerDTO(computerDao.findById(Long.valueOf(id)));
    }
    
    public void nextPage(){
        page.next();
        updatePage();
    }
    
    public void previousPage(){
        page.previous();
        updatePage();
    }
    
    public void updatePage(){
        int newOffset = (page.getNumber()-1)*page.maxPerPage;
        page.setObjects(ComputerMapper.toComputerDTO(computerDao.findAll(page.maxPerPage, newOffset)));
    }

    public Page<ComputerDTO> getPage() {
        return this.page;
    }
}