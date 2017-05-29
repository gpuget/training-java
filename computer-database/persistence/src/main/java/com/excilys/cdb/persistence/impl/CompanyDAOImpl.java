package com.excilys.cdb.persistence.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.UnauthorizedValueDAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;

@Repository("companyDao")
public class CompanyDAOImpl implements CompanyDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    /**
     * Initialization.
     */
    @PostConstruct
    public void init() {
        LOGGER.info("Initialization CompanyDAO...");
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        LOGGER.debug("Criteria builder initialized");
    }

    @Override
    @Transactional
    public Company create(Company company) {
        LOGGER.info("Create Company : " + company);

        try {
            entityManager.persist(company);
            LOGGER.debug("Generated Id : " + company.getId());
        } catch (PersistenceException e) {
            String message = "Error : DAO has not been able to correctly create the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return company;
    }

    @Override
    @Transactional
    public void delete(long id) {
        LOGGER.info("Delete company by id : " + id);
        String message = "Error : DAO has not been able to correctly delete the entity.";

        if (id > 0) {
            try {
                CriteriaDelete<Company> delete = criteriaBuilder
                        .createCriteriaDelete(Company.class);
                Root<Company> cpy = delete.from(Company.class);
                delete.where(criteriaBuilder.equal(cpy.get("id"), id));

                entityManager.createQuery(delete).executeUpdate();
            } catch (PersistenceException e) {
                LOGGER.error(message);
                throw new DAOException(message, e);
            }
        } else {
            LOGGER.error(message);
            throw new UnauthorizedValueDAOException(message);
        }
    }

    @Override
    public List<Company> findAll() {
        LOGGER.info("Find all companies.");
        try {
            CriteriaQuery<Company> find = criteriaBuilder.createQuery(Company.class);
            find.select(find.from(Company.class));

            return entityManager.createQuery(find).getResultList();
        } catch (PersistenceException e) {
            String message = "Error : DAO has not been able to find the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public Company findById(long id) {
        LOGGER.info("Find company by id : " + id);
        String message = "Error : DAO has not been able to find the entity.";

        if (id > 0) {
            try {
                CriteriaQuery<Company> find = criteriaBuilder.createQuery(Company.class);
                Root<Company> cpy = find.from(Company.class);
                find.select(cpy).where(criteriaBuilder.equal(cpy.get("id"), id));

                return entityManager.createQuery(find).getSingleResult();
            } catch (NoResultException e) {
                LOGGER.warn(e.getMessage());
                return null;
            } catch (PersistenceException e) {
                LOGGER.error(message);
                throw new DAOException(message, e);
            }
        } else {
            LOGGER.error(message);
            throw new UnauthorizedValueDAOException(message);
        }
    }

    @Override
    @Transactional
    public Company update(Company company) {
        LOGGER.info("Update company :" + company);

        try {
            CriteriaUpdate<Company> update = criteriaBuilder.createCriteriaUpdate(Company.class);
            Root<Company> com = update.from(Company.class);
            update.set(com.get("name"), company.getName())
                    .where(criteriaBuilder.equal(com.get("id"), company.getId()));

            entityManager.createQuery(update).executeUpdate();
        } catch (PersistenceException e) {
            String message = "Error : DAO has not been able to correctly update the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return company;
    }

    /**
     * Sets the entity manager.
     *
     * @param entityManager entity manager to set
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
