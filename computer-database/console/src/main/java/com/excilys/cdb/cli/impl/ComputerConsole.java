package com.excilys.cdb.cli.impl;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.stereotype.Component;

import com.excilys.cdb.cli.Console;
import com.excilys.cdb.model.dto.ComputerDTO;

@Component
public class ComputerConsole implements Console<ComputerDTO> {


    private final static String ROOT_URI = "http://localhost:8080/computer-database/api/";
    
    private ClientConfig authentification(){
        ClientConfig clientConfig = new ClientConfig();
        
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("saraadmin", "123456");
        clientConfig.register( feature) ;
        
        clientConfig.register(JacksonFeature.class);
        return clientConfig;
    }
    

    @Override
    public void add(ComputerDTO computerDto) {
        
        Client client = ClientBuilder.newClient();
        WebTarget base = client.target(ROOT_URI);
        WebTarget find = base.path("computers/");
        Invocation.Builder builder = find.request(MediaType.APPLICATION_JSON_TYPE);
        Response response=builder.post(Entity.entity(computerDto, MediaType.APPLICATION_JSON_TYPE));
        System.out.println(response.getStatus());
        
    }

    @Override
    public void delete(String id) {
        Client client = ClientBuilder.newClient();
        
            Response response= client.target(ROOT_URI)
                                        .path("computer")
                                            .path(id)
                                                .request().delete();
        //System.out.println(response.getStatus());
        
        
    }

    @Override
    public List<ComputerDTO> display() {
        Client client = ClientBuilder.newClient();
        ComputerDTO[] response= client.target(ROOT_URI)
                                        .path("computers")
                                            .request(MediaType.APPLICATION_JSON_TYPE)
                                                .get(ComputerDTO[].class);
        
        return Arrays.asList(response);
        
    }

  
    public void update(ComputerDTO computerDto) {
        Client client = ClientBuilder.newClient();
        WebTarget base = client.target(ROOT_URI);
        WebTarget find = base.path("computers/");
        Invocation.Builder builder = find.request(MediaType.APPLICATION_JSON_TYPE);
        builder.post(Entity.entity(computerDto, MediaType.APPLICATION_JSON_TYPE));
    }


    @Override
    public ComputerDTO findById(String id) {
        Client client = ClientBuilder.newClient();
        ComputerDTO response= client.target(ROOT_URI)
                                        .path("computer/")
                                            .path(id)
                                            .request(MediaType.APPLICATION_JSON_TYPE)
                                                .get(ComputerDTO.class);
        
        return response;
    }


    @Override
    public void update() {
        
    }
   

}
