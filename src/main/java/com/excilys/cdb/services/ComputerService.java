package com.excilys.cdb.services;

import java.util.List;

import com.excilys.cdb.models.Computer;
import com.excilys.cdb.models.Page;
import com.excilys.cdb.persistences.ComputerDAO;
import com.excilys.cdb.persistences.ComputerDAOImpl;

public class ComputerService {
    private ComputerDAO computerDao;

    /**
     * Constructor.
     */
    public ComputerService() {
        this.computerDao = new ComputerDAOImpl();
    }

    /**
     * Inserts the computer.
     * @param computer computer to insert
     * @return inserted computer
     */
    public Computer create(Computer computer) {
        return computerDao.create(computer);
    }

    /**
     * Deletes the computer corresponding to the identifier.
     * @param id identifier
     */
    public void delete(long id) {
        computerDao.delete(id);
    }

    /**
     * Deletes the computers corresponding to the identifiers.
     * @param idsList identifiers
     */
    public void deleteList(List<Long> idsList) {
        computerDao.delete(idsList);
    }

    /**
     * Updates the computer corresponding to the identifier after conversion.
     * @param computer computer to modify
     */
    public void update(Computer computer) {
        computerDao.update(computer);
    }

    /**
     * Gets the details of the computer corresponding to the identifier.
     * @param id identifier
     * @return computer
     */
    public Computer getDetails(long id) {
        return computerDao.findById(id);
    }
    
    /**
     * Gets the total number of computers.
     * @return total number of computers
     */
    public int getCount(){
        return computerDao.getCount();
    }
    
    /**
     * Gets the page of computers.
     * @param number number of the page
     * @param maxPerPage maximum of items
     * @return
     */
    public Page<Computer> getPage(int number, int maxPerPage) {
        return new Page<>(number, computerDao.findAll(maxPerPage, maxPerPage * (number - 1)));
    }
    
    public Page<Computer> getFilteredByNamePage(int number, int maxPerPage, String name){
        return new Page<>(number, computerDao.getFilteredByName(maxPerPage, maxPerPage * (number - 1), name));
    }
    
    /**
     * Gets the total number of computers.
     * @return total number of computers
     */
    public int getFilteredByNameCount(String name){
        return computerDao.getFilteredByNameCount(name);
    }
}