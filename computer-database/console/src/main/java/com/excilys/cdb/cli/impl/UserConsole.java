package com.excilys.cdb.cli.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.excilys.cdb.cli.Console;
import com.excilys.cdb.model.auth.User;
import com.excilys.cdb.model.auth.UserRole;
import com.excilys.cdb.persistence.UserDAO;

@Component
public class UserConsole implements Console<User> {
    @Autowired
    private UserDAO userDao;

    private Scanner scanner = new Scanner(System.in);
    
    @Override
    public void add(User user) {
        System.out.print("Name : ");
        String name = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();
        user.setUsername(name);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setEnabled(true);
        userDao.create(user);
        addRoles(user);
    }

    @Override
    public void delete(String id) {
        display();
        System.out.print("Name : ");
        String name = scanner.nextLine();
        userDao.delete(name);
    }

    @Override
    public List<User> display() {
        List<User> users = userDao.findAll();
        for (User usr : users) {
            System.out.println(usr);
        }
        return users;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }

    /**
     * Add roles for an user.
     *
     * @param user user to configure
     */
    private void addRoles(User user) {
        Set<UserRole> res = new HashSet<>();

        System.out.print("Roles : ");
        String roles = scanner.nextLine();

        for (String s : roles.split(" ")) {
            UserRole ur = new UserRole(user, s);
            res.add(ur);
            userDao.create(ur);
        }
    }


    @Override
    public User findById(String id) {
        return null;
        // TODO Auto-generated method stub
        
    }
}
