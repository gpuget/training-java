package com.excilys.cdb.cli.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

@Service
public class ConsoleComputerService extends AbstractConsoleService {
    @Autowired
    private ComputerDAO computerDao;
    
    @Autowired
    private ConsoleCompanyService companyService;
    
    @Override
    public void add() {
        System.out.print("Computer name : ");
        String name = scanner.next();
        companyService.display();
        System.out.print("Company id : ");
        long companyId = scanner.nextLong();
        System.out.print("Introduced (yyyy MM dd): ");
        LocalDate i = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        System.out.print("Discontinued (yyyy MM dd): ");
        LocalDate d = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());

        Computer computer = new Computer.Builder().name(name)
                .manufacturer(new Company.Builder().id(companyId).build()).introduced(i)
                .discontinued(d).build();

        computerDao.create(computer);
    }

    @Override
    public void delete() {
        display();
        System.out.print("Computer id :");
        long id = scanner.nextLong();

        computerDao.delete(id);
    }

    @Override
    public void display() {
        for (Computer cpu : computerDao.findAll()) {
            System.out.println(cpu);
        }
    }

    @Override
    public void update() {
        display();
        System.out.print("Computer id : ");
        long id = scanner.nextLong();
        System.out.print("Computer name : ");
        String name = scanner.next();
        companyService.display();
        System.out.print("Company id : ");
        long companyId = scanner.nextLong();
        System.out.print("Introduced (yyyy MM dd): ");
        LocalDate i = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        System.out.print("Discontinued (yyyy MM dd): ");
        LocalDate d = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());

        Computer computer = new Computer.Builder().id(id).name(name)
                .manufacturer(new Company.Builder().id(companyId).build()).introduced(i)
                .discontinued(d).build();

        computerDao.update(computer);
    }
}
