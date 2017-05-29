package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.auth.User;
import com.excilys.cdb.model.auth.UserRole;

public interface UserDAO {
    /**
     * Inserts the user in DB.
     *
     * @param user user to insert
     * @return user
     */
    User create(User user);

    /**
     * Inserts the role in DB.
     *
     * @param role role to insert
     * @return role
     */
    UserRole create(UserRole role);

    /**
     * Delete user from DB corresponding to username.
     *
     * @param username name
     */
    void delete(String username);

    /**
     * Select users in DB.
     *
     * @return found users
     */
    List<User> findAll();

    /**
     * Select user by name.
     *
     * @param username name to find
     * @return found user
     */
    User findByUsername(String username);
}