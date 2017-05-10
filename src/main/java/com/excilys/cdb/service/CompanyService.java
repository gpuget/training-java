package com.excilys.cdb.service;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.Connector;
import com.excilys.cdb.persistence.impl.CompanyDAOImpl;
import com.excilys.cdb.persistence.impl.ComputerDAOImpl;

public enum CompanyService {
	INSTANCE;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    public List<Company> getCompanies() {
        return CompanyDAOImpl.INSTANCE.findAll();
    }
    
    public Company getCompanyById(long id) {
        return CompanyDAOImpl.INSTANCE.findById(id);
    }
    
    public Company create(Company company) {
    	return CompanyDAOImpl.INSTANCE.create(company);
    }
    
    public void delete(long id) {
    	ThreadLocal<Connection> sharedConnectionThread = new ThreadLocal<>();
    	
    	try (Connection connection = Connector.INSTANCE.getConnection()) {
    		sharedConnectionThread.set(connection);
    		ComputerDAOImpl.INSTANCE.deleteFromCompany(id, sharedConnectionThread.get());
    		CompanyDAOImpl.INSTANCE.delete(id, sharedConnectionThread.get());
    	} catch (Exception e) {
        	String message = "Error : Service has not been able to correctly delete the company and its computers.";
        	LOGGER.error(message);
            throw new RuntimeException(message, e);
    	} finally {
    		sharedConnectionThread.remove();
    	}
    }
}
