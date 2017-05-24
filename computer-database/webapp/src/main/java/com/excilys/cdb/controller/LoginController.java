package com.excilys.cdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class LoginController {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    

    @GetMapping(value = "/login")
    public String get() {
        return "login";
    }
    
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @GetMapping(value = "/errors/403")
    public String acessDenied() {
        return "errors/403";
    }
}