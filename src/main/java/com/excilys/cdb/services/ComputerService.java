package com.excilys.cdb.services;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.models.Computer;
import com.excilys.cdb.models.Page;
import com.excilys.cdb.persistences.ComputerDAO;
import com.excilys.cdb.persistences.ComputerDAOImpl;

public class ComputerService {
    private ComputerDAO computerDao;
    private Page<ComputerDTO> page;
    private int maxPerPage = 10;

    /**
     * Constructor.
     */
    public ComputerService() {
        this.computerDao = new ComputerDAOImpl();
        this.page = new Page<>(ComputerMapper.toComputerDTO(computerDao.findAll(maxPerPage, 0)));
    }

    /**
     * Inserts the computer after a conversion.
     * @param computerDto DTO to convert and insert
     * @return inserted computer
     */
    public Computer create(ComputerDTO computerDto) {
        return computerDao.create(ComputerMapper.toComputer(computerDto));
    }

    /**
     * Deletes the computer corresponding to the identifier.
     * @param id identifier
     */
    public void delete(String id) {
        computerDao.delete(Long.valueOf(id));
    }

    /**
     * Updates the computer corresponding to the identifier after conversion.
     * @param computerDto modified DTO to convert and update
     */
    public void update(ComputerDTO computerDto) {
        computerDao.update(ComputerMapper.toComputer(computerDto));
    }

    /**
     * Gets the details of the computer corresponding to the identifier.
     * @param id identifier
     * @return converted computer
     */
    public ComputerDTO getDetails(String id) {
        return ComputerMapper.toComputerDTO(computerDao.findById(Long.valueOf(id)));
    }

    /**
     * Goes to the next page.
     */
    public void nextPage() {
        page.next();
        updatePage();
    }

    /**
     * Goes to the previous page.
     */
    public void previousPage() {
        page.previous();
        updatePage();
    }

    /**
     * Updates the page.
     */
    private void updatePage() {
        int newOffset = (page.getNumber() - 1) * page.maxPerPage;
        page.setObjects(ComputerMapper.toComputerDTO(computerDao.findAll(
                page.maxPerPage, newOffset)));
    }
    
    /**
     * Gets the total number of computers.
     * @return total number of computers
     */
    public int getTotal(){
        return computerDao.getTotal();
    }

    public Page<ComputerDTO> getPage() {
        updatePage();
        return this.page;
    }
    
    public Page<ComputerDTO> getPage(int number){
        page.setNumber(number);
        updatePage();
        return this.page;
    }
    
    public int getMaxPerPage(){
        return this.maxPerPage;
    }
}