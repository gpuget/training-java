package com.excilys.cdb.persistence;

import com.excilys.cdb.model.User;

public interface UserDAO {
    User create(User user);
    User findByUsername(String username);
}
