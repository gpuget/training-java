package com.excilys.cdb.persistence;

import com.excilys.cdb.model.auth.User;

public interface UserDAO {
    /**
     * Inserts the user in DB.
     *
     * @param user user to insert
     * @return user
     */
    User create(User user);

    /**
     * Select user by name.
     *
     * @param username name to find
     * @return found user
     */
    User findByUsername(String username);
}