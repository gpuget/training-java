package com.excilys.cdb.persistences;

import java.util.List;

import com.excilys.cdb.models.Computer;

public interface ComputerDAO {
	public Computer create(Computer computer);
	public Computer findById(Long id);
	public List<Computer> findAll();
	public void update(Computer computer);
	public void delete(Computer computer);
}
