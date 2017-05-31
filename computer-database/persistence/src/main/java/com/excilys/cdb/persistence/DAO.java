package com.excilys.cdb.persistence;

import java.util.List;

public interface DAO<E, K> {
    /**
     * Inserts an object in DB.
     *
     * @param obj object to insert
     * @return inserted object with its primary key
     */
    E create(E obj);

    /**
     * Deletes the object corresponding to the primary key in DB.
     *
     * @param key primary key
     */
    void delete(K key);

    /**
     * Finds all objects in DB.
     *
     * @return list of all objects in DB
     */
    List<E> findAll();

    /**
     * Finds the object corresponding to the primary key in DB.
     *
     * @param key primary key
     * @return found object
     */
    E find(K key);

    /**
     * Updates company in DB.
     *
     * @param company modified company
     * @return company
     */
    E update(E obj);
}
