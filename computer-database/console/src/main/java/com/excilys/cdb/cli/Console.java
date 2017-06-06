package com.excilys.cdb.cli;

import java.util.List;

public interface Console<T> {
    /**
     * Add data.
     */
    void add(T t);

    /**
     * Delete data.
     */
    void delete(String id);

    /**
     * Display data.
     */
    List<T> display();

    /**
     * Update data.
     */
    void update();
    
    T findById(String id);
}
