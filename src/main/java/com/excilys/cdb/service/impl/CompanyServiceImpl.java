package com.excilys.cdb.service.impl;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.persistence.Connector;
import com.excilys.cdb.service.CompanyService;

public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private CompanyDAO companyDao;
    private ComputerDAO computerDao;

    @Override
    public List<Company> getCompanies() {
        LOGGER.info("Get companies");
        return companyDao.findAll();
    }

    @Override
    public Company getCompanyById(long id) {
        LOGGER.info("Get company by id : " + id);
        return companyDao.findById(id);
    }

    @Override
    public Company create(Company company) {
        LOGGER.info("Create company : " + company);
        return companyDao.create(company);
    }

    @Override
    public void delete(long id) {
        LOGGER.info("Delete company by id : " + id);
        ThreadLocal<Connection> sharedConnectionThread = new ThreadLocal<>();

        try (Connection connection = new Connector().getConnection()) {
            LOGGER.debug("Connection : " + connection);
            LOGGER.debug("Connection auto commit : false");
            connection.setAutoCommit(false);
            sharedConnectionThread.set(connection);
            try {
                computerDao.deleteFromCompany(id, sharedConnectionThread.get());
                companyDao.delete(id, sharedConnectionThread.get());
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

    /**
     * Sets the company DAO.
     *
     * @param companyDao company DAO to use
     */
    public void setCompanyDao(CompanyDAO companyDao) {
        LOGGER.info("Set company DAO : " + companyDao);
        this.companyDao = companyDao;
    }

    /**
     * Sets the computer DAO.
     *
     * @param computerDao computer DAO to use
     */
    public void setComputerDao(ComputerDAO computerDao) {
        LOGGER.info("Set computer DAO : " + computerDao);
        this.computerDao = computerDao;
    }
}
