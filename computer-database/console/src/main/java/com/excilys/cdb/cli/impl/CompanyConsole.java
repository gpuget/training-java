package com.excilys.cdb.cli.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.cli.Console;
import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.service.CompanyService;

@Component
public class CompanyConsole implements Console<CompanyDTO> {
    @Autowired
    private CompanyService companyService;

    private Scanner scanner = new Scanner(System.in);
    
    private final static String ROOT_URI = "http://localhost:8080/computer-database/api/";

    
    @Override
    public void add(CompanyDTO companyDto) {
        
        Client client = ClientBuilder.newClient();
        WebTarget base = client.target(ROOT_URI);
        WebTarget find = base.path("companies/");
        Invocation.Builder builder = find.request(MediaType.APPLICATION_JSON_TYPE);
        Response response=builder.post(Entity.entity(companyDto, MediaType.APPLICATION_JSON_TYPE));
        System.out.println(response.getStatus());
    }

    @Override
    public void delete(String id) {

        Client client = ClientBuilder.newClient();
        
        Response response= client.target(ROOT_URI)
                                    .path("company")
                                    .path(id)
                                            .request().delete();
    System.out.println(response.getStatus());
    
    }

    @Override
    public List<CompanyDTO> display() {
        
          Client client = ClientBuilder.newClient();
        CompanyDTO[] response= client.target(ROOT_URI)
                                        .path("companies")
                                            .request(MediaType.APPLICATION_JSON_TYPE)
                                                .get(CompanyDTO[].class);
        
        return Arrays.asList(response);
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

    @Override
    public CompanyDTO findById(String id) {
        Client client = ClientBuilder.newClient();
        CompanyDTO response= client.target(ROOT_URI)
                                        .path("company/")
                                            .path(id)
                                            .request(MediaType.APPLICATION_JSON_TYPE)
                                                .get(CompanyDTO.class);
        
        return response;
    }
}
