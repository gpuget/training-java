package com.excilys.cdb.cli.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.service.CompanyService;

@Component
public class CompanyConsole extends AbstractConsole {
    @Autowired
    private CompanyService companyService;

    @Override
    public void add() {
        System.out.print("Company name : ");
        String name = scanner.next();
        
        CompanyDTO companyDto = new CompanyDTO();
        companyDto.setName(name);

        companyService.create(companyDto);
    }

    @Override
    public void delete() {
        display();
        System.out.print("Company id :");
        long id = scanner.nextLong();

        companyService.delete(id);
    }

    @Override
    public void display() {
        for (CompanyDTO comDto : companyService.getCompanies()) {
            System.out.println(comDto);
        }
    }

    @Override
    public void update() {
        display();
        System.out.print("Company id : ");
        long id = scanner.nextLong();
        System.out.print("Company name : ");
        String name = scanner.next();
        
        CompanyDTO companyDto = new CompanyDTO(id, name);

        companyService.update(companyDto);
    }
}
