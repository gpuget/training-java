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

    /**
     * Returns all companies.
     *
     * @return found companies
     */
    public List<Company> getCompanies() {
        LOGGER.trace("Get companies");
        return new CompanyDAOImpl().findAll();
    }

    /**
     * Returns the Company corresponding to the identifier.
     *
     * @param id identifier
     * @return found company
     */
    public Company getCompanyById(long id) {
        LOGGER.trace("Get company by id : " + id);
        return new CompanyDAOImpl().findById(id);
    }

    /**
     * Inserts a company in DB.
     *
     * @param company to insert
     * @return company with identifier
     */
    public Company create(Company company) {
        LOGGER.trace("Create company : " + company);
        return new CompanyDAOImpl().create(company);
    }

    /**
     * Deletes the company corresponding to the identifier and its computers.
     *
     * @param id identifier
     */
    public void delete(long id) {
        LOGGER.trace("Delete company by id : " + id);
        ThreadLocal<Connection> sharedConnectionThread = new ThreadLocal<>();

        try (Connection connection = new Connector().getConnection()) {
            LOGGER.debug("Connection : " + connection);
            LOGGER.debug("Connection auto commit : false");
            connection.setAutoCommit(false);
            sharedConnectionThread.set(connection);
            try {
                new ComputerDAOImpl().deleteFromCompany(id, sharedConnectionThread.get());
                new CompanyDAOImpl().delete(id, sharedConnectionThread.get());
            } catch (Exception e) {
                LOGGER.debug("Connection rollback");
                connection.rollback();
                throw e;
            }
            LOGGER.debug("Connection commit");
            connection.commit();
        } catch (Exception e) {
            String message = "Error : Service has not been able to correctly delete the company and its computers.";
            LOGGER.error(message);
            throw new RuntimeException(message, e);
        } finally {
            sharedConnectionThread.remove();
            LOGGER.debug("Garbage collector called");
            System.gc();
        }
    }
}
