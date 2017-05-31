package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.dto.ComputerDTO;

public interface ComputerService {

    /**
     * Inserts the computer.
     *
     * @param computerDto computer to insert
     * @return inserted computer
     */
    ComputerDTO create(ComputerDTO computerDto);

    /**
     * Deletes the computers corresponding to the identifiers.
     *
     * @param idsList identifiers
     */
    void deleteList(List<Long> idsList);

    /**
     * Gets the details of the computer corresponding to the identifier.
     *
     * @param id identifier
     * @return computer
     */
    ComputerDTO getComputerById(long id);

    /**
     * Gets the all computers.
     *
     * @return computers
     */
    List<ComputerDTO> getComputers();

    /**
     * Gets the pages of computers corresponding to specified name.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @param name seek name
     * @return page of filtered computers
     */
    Page<ComputerDTO> getFilteredByNamePage(int number, int maxPerPage, String name);

    /**
     * Gets the page of computers.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @return page page of computers
     */
    Page<ComputerDTO> getPage(int number, int maxPerPage);

    /**
     * Updates the computer corresponding to the identifier after conversion.
     *
     * @param computerDto modified computer
     * @return modified computer
     */
    ComputerDTO update(ComputerDTO computerDto);
}
