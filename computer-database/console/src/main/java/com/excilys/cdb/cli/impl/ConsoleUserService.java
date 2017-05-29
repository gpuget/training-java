package com.excilys.cdb.cli.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.auth.User;
import com.excilys.cdb.model.auth.UserRole;
import com.excilys.cdb.persistence.UserDAO;

@Service
public class ConsoleUserService extends AbstractConsoleService {
    @Autowired
    private UserDAO userDao;

    @Override
    public void add() {
        System.out.print("Name : ");
        String name = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();
        User user = new User(name, password, true);

        userDao.create(user);
        addRoles(user);
    }

    @Override
    public void delete() {
        display();
        System.out.print("Name : ");
        String name = scanner.nextLine();
        userDao.delete(name);
    }

    @Override
    public void display() {
        for (User usr : userDao.findAll()) {
            System.out.println(usr);
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

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
}
