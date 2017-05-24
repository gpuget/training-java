package com.excilys.cdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class LoginController {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String get() {
        return "login";
    }
    
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @RequestMapping(value = "/errors/403", method = RequestMethod.GET)
    public String acessDenied() {
        return "errors/403";
    }
}