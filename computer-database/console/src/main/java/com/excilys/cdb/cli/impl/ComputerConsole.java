package com.excilys.cdb.cli.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.dto.ComputerDTO;
import com.excilys.cdb.service.ComputerService;

@Component
public class ComputerConsole extends AbstractConsole {
    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyConsole companyService;

    @Override
    public void add() {
        System.out.print("Computer name : ");
        String name = scanner.next();
        companyService.display();
        System.out.print("Company id : ");
        long companyId = scanner.nextLong();
        System.out.print("Introduced (yyyy-MM-dd): ");
        String introduced = scanner.next();
        System.out.print("Discontinued (yyyy-MM-dd): ");
        String discontinued = scanner.next();

        ComputerDTO computerDto = new ComputerDTO();
        computerDto.setName(name);
        computerDto.setCompanyId(companyId);
        computerDto.setDiscontinued(discontinued);
        computerDto.setIntroduced(introduced);

        computerService.create(computerDto);
    }

    @Override
    public void delete() {
        display();
        System.out.print("Computer id :");
        long id = scanner.nextLong();

        computerService.delete(id);
    }

    @Override
    public void display() {
        for (ComputerDTO cpuDto : computerService.getComputers()) {
            System.out.println(cpuDto);
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
        System.out.print("Introduced (yyyy-MM-dd): ");
        String introduced = scanner.next();
        System.out.print("Discontinued (yyyy-MM-dd): ");
        String discontinued = scanner.next();

        ComputerDTO computerDto = new ComputerDTO();
        computerDto.setId(id);
        computerDto.setName(name);
        computerDto.setCompanyId(companyId);
        computerDto.setDiscontinued(discontinued);
        computerDto.setIntroduced(introduced);

        computerService.update(computerDto);
    }
}
