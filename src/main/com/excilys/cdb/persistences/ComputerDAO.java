package com.excilys.cdb.persistences;

import java.util.List;

import com.excilys.cdb.models.Computer;

public interface ComputerDAO {
	public void create(Computer computer);
	public Computer findById(Long id);	
	public List<Computer> findByName(String name);
	public List<Computer> findAll();
}
