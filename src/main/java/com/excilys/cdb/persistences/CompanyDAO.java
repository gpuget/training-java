package com.excilys.cdb.persistences;

import java.util.List;

import com.excilys.cdb.models.Company;

public interface CompanyDAO {
	public List<Company> findAll();
}
