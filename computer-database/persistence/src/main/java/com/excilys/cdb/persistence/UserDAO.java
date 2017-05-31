package com.excilys.cdb.persistence;

import com.excilys.cdb.model.auth.User;
import com.excilys.cdb.model.auth.UserRole;

public interface UserDAO extends DAO<User, String>{
    /**
     * Inserts a role in DB.
     *
     * @param role role to insert
     * @return inserted role with its primary key
     */
    UserRole create(UserRole role);
}