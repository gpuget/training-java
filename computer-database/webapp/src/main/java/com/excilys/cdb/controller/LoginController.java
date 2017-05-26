package com.excilys.cdb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @GetMapping(value = "/login")
    public ModelAndView login(  @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout) {
        LOGGER.info("login GET");
        ModelAndView model = new ModelAndView();
        String message = null;

        if (error != null) {
            message = "Invalid username and password !";
            LOGGER.debug(message);
            model.addObject("error", message);
        }

        if (logout != null) {
            message = "You have been logged out !";
            LOGGER.debug(message);
            model.addObject("logout", message);
        }

        return model;
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.info("logout GET");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(req, resp, auth);
            LOGGER.debug("Authentification : " + auth);
        } else {
            LOGGER.warn("No Authentification logged out");
        }
        
        return "redirect:/login?logout";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @GetMapping(value = "/errors/403")
    public String acessDenied() {
        LOGGER.warn("403 accessDenied");
        return "errors/403";
    }
}