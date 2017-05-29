package com.excilys.cdb.cli.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;

@Service
public class ConsoleCompanyService extends AbstractConsoleService {
    @Autowired
    private CompanyDAO companyDao;

    @Override
    public void add() {
        System.out.print("Company name : ");
        String name = scanner.next();

        companyDao.create(new Company.Builder().name(name).build());
    }

    @Override
    public void delete() {
        display();
        System.out.print("Company id :");
        long id = scanner.nextLong();

        companyDao.delete(id);
    }

    @Override
    public void display() {
        for (Company com : companyDao.findAll()) {
            System.out.println(com);
        }
    }

    @Override
    public void update() {
        display();
        System.out.print("Company id : ");
        long id = scanner.nextLong();
        System.out.print("Company name : ");
        String name = scanner.next();

        companyDao.update(new Company.Builder().id(id).name(name).build());
    }
}
