package com.excilys.cdb.persistence.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.UnauthorizedValueDAOException;
import com.excilys.cdb.mapper.row.ComputerRowMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

@Repository("computerDao")
public class ComputerDAOImpl implements ComputerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAOImpl.class);

    private static final String FIND_QUERY = "SELECT cpu.id, cpu.name, introduced, discontinued, company_id, com.name AS company_name FROM computer AS cpu LEFT JOIN company AS com ON company_id = com.id WHERE cpu.id = ?";
    private static final String FIND_ALL = "SELECT cpu.id, cpu.name, introduced, discontinued, company_id, com.name AS company_name FROM computer AS cpu LEFT JOIN company AS com ON company_id = com.id";
    private static final String DELETE_QUERY = "DELETE FROM computer WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
    private static final String DELETE_IN = "DELETE FROM computer WHERE id IN";
    private static final String DELETE_FROM_COMPANY = "DELETE FROM computer WHERE company_id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(id) FROM computer";

    private static final String BOUNDED_RESULT = " LIMIT :limit OFFSET :offset";
    private static final String LIKE_NAME = " WHERE cpu.name LIKE :name";

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
            Root<Computer> computer = delete.from(Computer.class);
            delete.where(computer.get("id").in(idsList));
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
    public void delete(long id) {
        LOGGER.info("Delete computer by id : " + id);
        String message = "Error : DAO has not been able to correctly delete the entity.";

        if (id > 0) {
            try {
                CriteriaDelete<Computer> delete = criteriaBuilder.createCriteriaDelete(Computer.class);
                Root<Computer> computer = delete.from(Computer.class);
                delete.where(criteriaBuilder.equal(computer.get("id"), id));
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
    @Transactional
    public void deleteFromCompany(long companyId) {
        LOGGER.info("Delete computer from company : " + companyId);
        String message = "Error : DAO has not been able to correctly delete the entity.";

        if (companyId > 0) {
            try {
                CriteriaDelete<Computer> delete = criteriaBuilder.createCriteriaDelete(Computer.class);
                Root<Computer> computer = delete.from(Computer.class);
                delete.where(criteriaBuilder.equal(computer.get("company_id"), companyId));
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
    public List<Computer> findAll() {
        LOGGER.info("Find all computers.");
        try {
            LOGGER.debug("Query : " + FIND_ALL);
            return jdbcTemplate.query(FIND_ALL, new ComputerRowMapper());
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to correctly find all entities.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    @Override
    public List<Computer> findAll(int limit, int offset) {
        LOGGER.info("Find all computers : " + limit + " " + offset);
        String message = "Error : DAO has not been able to correctly find all entities.";

        if (limit > 0 && offset >= 0) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Computer> find = cb.createQuery(Computer.class);
                find.select(find.from(Computer.class));
                LOGGER.debug("Criteria query : " + find);
                TypedQuery<Computer> query = entityManager.createQuery(find).setMaxResults(limit)
                        .setFirstResult(offset);

                return query.getResultList();
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
                LOGGER.debug("Query : " + FIND_QUERY);
                List<Computer> result = jdbcTemplate.query(FIND_QUERY, new ComputerRowMapper(), id);
                return (!result.isEmpty() ? result.get(0) : null);
            } catch (DataAccessException e) {
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
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("name", name + '%');
            parameters.addValue("limit", limit);
            parameters.addValue("offset", offset);
            try {
                String sqlQuery = FIND_ALL + LIKE_NAME + BOUNDED_RESULT;
                LOGGER.debug("Query : " + sqlQuery);
                return new NamedParameterJdbcTemplate(jdbcTemplate).query(sqlQuery, parameters,
                        new ComputerRowMapper());
            } catch (DataAccessException e) {
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
            LOGGER.debug("Query : " + COUNT_QUERY);
            return jdbcTemplate.queryForObject(COUNT_QUERY, Integer.class);
        } catch (DataAccessException e) {
            LOGGER.error(message);
            throw new DAOException(message, e);
        }
    }

    /**
     * Sets values in the prepared statement.
     *
     * @param ps statement to be loaded
     * @param computer computer with values to load in statement
     * @throws SQLException SQL exception
     */
    public void setStatementValues(PreparedStatement ps, Computer computer) throws SQLException {
        LOGGER.info("Set values in statement");
        Company company = computer.getManufacturer();

        ps.setString(1, computer.getName());
        ps.setLong(4, company.getId());

        if (computer.getIntroduced() != null) {
            ps.setDate(2, Date.valueOf(computer.getIntroduced()));
        } else {
            ps.setDate(2, null);
        }

        if (computer.getDiscontinued() != null) {
            ps.setDate(3, Date.valueOf(computer.getDiscontinued()));
        } else {
            ps.setDate(3, null);
        }
    }

    @Override
    public Computer update(Computer computer) {
        LOGGER.info("Update computer :" + computer);

        try {
            jdbcTemplate.update(UPDATE_QUERY, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    setStatementValues(ps, computer);
                    ps.setLong(5, computer.getId());
                    LOGGER.debug("Query : " + ps);
                }
            });
        } catch (DataAccessException e) {
            String message = "Error : DAO has not been able to correctly update the entity.";
            LOGGER.error(message);
            throw new DAOException(message, e);
        }

        return computer;
    }

    /**
     * @param entityManager the entity manager to set
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
