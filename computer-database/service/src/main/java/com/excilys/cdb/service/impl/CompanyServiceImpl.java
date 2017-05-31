package com.excilys.cdb.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.mapper.dto.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.CompanyService;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyDAO companyDao;

    @Autowired
    private ComputerDAO computerDao;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CompanyDTO create(CompanyDTO companyDto) {
        LOGGER.info("Create company : " + companyDto);
        Company company = companyDao.create(companyMapper.toCompany(companyDto));
        return companyMapper.toCompanyDTO(company);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(long id) {
        LOGGER.info("Delete company by id : " + id);
        computerDao.deleteFromCompany(id);
        companyDao.delete(id);
    }

    @Override
    public List<CompanyDTO> getCompanies() {
        LOGGER.info("Get companies");
        return companyMapper.toCompanyDTO(companyDao.findAll());
    }

    @Override
    public CompanyDTO getCompanyById(long id) {
        LOGGER.info("Get company by id : " + id);
        return companyMapper.toCompanyDTO(companyDao.findById(id));
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
}
