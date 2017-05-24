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
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

@Repository("computerDao")
public class ComputerDAOImpl implements ComputerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    @PostConstruct
    public void init() {
        LOGGER.info("Initialization Computer DAO...");
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        LOGGER.debug("Criteria builder initialized");
    }

    @Override
    @Transactional
    public Computer create(Computer computer) {
        LOGGER.info("Create computer : " + computer);

        try {
            entityManager.persist(computer);
            LOGGER.debug("Generated Id : " + computer.getId());
        } catch (PersistenceException e) {
            String message = "Error : DAO has not been able to correctly create the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return computer;
    }

    @Override
    @Transactional
    public void delete(List<Long> idsList) {
        LOGGER.info("Delete all computer in : " + idsList);
        try {
            CriteriaDelete<Computer> delete = criteriaBuilder.createCriteriaDelete(Computer.class);
            Root<Computer> cpu = delete.from(Computer.class);
            delete.where(cpu.get("id").in(idsList));
            LOGGER.debug("Criteria delete : " + delete);

            entityManager.createQuery(delete).executeUpdate();
        } catch (PersistenceException e) {
            String message = "Error : DAO has not been able to correctly delete entities.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    @Transactional
    public void deleteFromCompany(long companyId) {
        LOGGER.info("Delete computer from company : " + companyId);
        String message = "Error : DAO has not been able to correctly delete the entity.";

        if (companyId > 0) {
            try {
                CriteriaDelete<Computer> delete = criteriaBuilder
                        .createCriteriaDelete(Computer.class);
                Root<Computer> cpu = delete.from(Computer.class);
                delete.where(criteriaBuilder.equal(cpu.get("company_id"), companyId));
                LOGGER.debug("Criteria delete : " + delete);

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
    public List<Computer> findAll(int limit, int offset) {
        LOGGER.info("Find all computers : " + limit + " " + offset);
        String message = "Error : DAO has not been able to correctly find all entities.";

        if (limit > 0 && offset >= 0) {
            try {
                CriteriaQuery<Computer> find = criteriaBuilder.createQuery(Computer.class);
                find.select(find.from(Computer.class));
                LOGGER.debug("Criteria query : " + find);

                return entityManager.createQuery(find).setMaxResults(limit).setFirstResult(offset)
                        .getResultList();
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
    public Computer findById(long id) {
        LOGGER.info("Find computer by id : " + id);
        String message = "Error : DAO has not been able to find the entity.";

        if (id > 0) {
            try {
                CriteriaQuery<Computer> find = criteriaBuilder.createQuery(Computer.class);
                Root<Computer> cpu = find.from(Computer.class);
                find.select(cpu).where(criteriaBuilder.equal(cpu.get("id"), id));
                LOGGER.debug("Criteria query : " + find);

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
    public List<Computer> findByName(int limit, int offset, String name) {
        LOGGER.info("Find all computer with name : " + name);
        String message = "Error : DAO has not been able to correctly find all entities.";

        if (limit > 0 && offset >= 0) {
            try {
                CriteriaQuery<Computer> find = criteriaBuilder.createQuery(Computer.class);
                Root<Computer> cpu = find.from(Computer.class);
                find.select(cpu).where(criteriaBuilder.like(cpu.get("name"), name + '%'));
                LOGGER.debug("Criteria query : " + find);

                return entityManager.createQuery(find).setMaxResults(limit).setFirstResult(offset)
                        .getResultList();
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
    public int getCount() {
        LOGGER.info("Count computers");
        String message = "Error : DAO has not been able to correctly count the entities.";

        try {
            CriteriaQuery<Long> count = criteriaBuilder.createQuery(Long.class);
            count.select(criteriaBuilder.count(count.from(Computer.class).get("id")));

            return entityManager.createQuery(count).getSingleResult().intValue();
        } catch (PersistenceException e) {
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    @Transactional
    public Computer update(Computer computer) {
        LOGGER.info("Update computer :" + computer);

        try {
            CriteriaUpdate<Computer> update = criteriaBuilder.createCriteriaUpdate(Computer.class);
            Root<Computer> cpu = update.from(Computer.class);
            update.set(cpu.get("name"), computer.getName())
                    .set(cpu.get("introduced"), computer.getIntroduced())
                    .set(cpu.get("discontinued"), computer.getDiscontinued())
                    .set(cpu.get("manufacturer"), computer.getManufacturer())
                    .where(criteriaBuilder.equal(cpu.get("id"), computer.getId()));

            entityManager.createQuery(update).executeUpdate();
        } catch (PersistenceException e) {
            String message = "Error : DAO has not been able to correctly update the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return computer;
    }

    /**
     * Sets the entity manager.
     *
     * @param entityManager the entity manager to set
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
