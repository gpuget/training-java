package com.excilys.cdb.persistence.impl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.auth.User;
import com.excilys.cdb.persistence.UserDAO;

@Repository
public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    /**
     * Initialization.
     */
    @PostConstruct
    public void init() {
        LOGGER.info("Initialization UserDAO...");
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        LOGGER.debug("Criteria builder initialized");
    }

    @Override
    @Transactional
    public User create(User user) {
        LOGGER.info("Create User : " + user);

        try {
            entityManager.persist(user);
            LOGGER.debug("Entity stored.");
        } catch (PersistenceException e) {
            String message = "Error : DAO has not been able to correctly create the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return user;
    }

    @Override
    public User findByUsername(String username) {
        LOGGER.info("Find company by username : " + username);

        try {
            CriteriaQuery<User> find = criteriaBuilder.createQuery(User.class);
            Root<User> usr = find.from(User.class);
            find.select(usr).where(criteriaBuilder.equal(usr.get("username"), username));

            return entityManager.createQuery(find).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warn(e.getMessage());
            return null;
        } catch (PersistenceException e) {
            String message = "Error : DAO has not been able to find the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }
}