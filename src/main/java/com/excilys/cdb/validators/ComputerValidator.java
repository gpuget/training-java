package com.excilys.cdb.validators;

import java.time.LocalDate;
import java.util.Map;

import javax.servlet.ServletException;

import com.excilys.cdb.models.Company;
import com.excilys.cdb.models.Computer;
import com.excilys.cdb.services.CompanyService;

public final class ComputerValidator {
    public static boolean checkName(String name){
        return name.matches("[\\d\\w\\+\\-\\ \\.\\']+");
    }
    
    public static boolean checkDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    
    public static boolean checkId(String id) {
        return id.matches("\\d+") && Integer.parseInt(id) > 0;
    }
    
    public static Company getValidCompany(String companyId) throws ServletException {
        Company company;
        
        if (StringValidator.checkIsNumber(companyId)) {
            company = new CompanyService().getCompanyById(Long.parseLong(companyId));
            if (company != null) {
                return company;
            } else {
                throw new ServletException("Sorry, the company does not exist.");
            }
        } else {
            throw new ServletException("Sorry, the computer is not valid.");
        }
    }
    
    public static Computer getValidComputer(Map<String, String[]> parameters) throws ServletException {
        String paramValue;
        Computer computer = new Computer.Builder().build();
        LocalDate i = null;
        LocalDate d = null;
        
        if (parameters.containsKey("computerName")) {
            paramValue = parameters.get("computerName")[0];
            if (checkName(paramValue)) {
                computer.setName(paramValue);
            } else {
                throw new ServletException("Sorry, the computer name is not valid : " + paramValue);
            }
        } else {
            throw new ServletException("Sorry, the computer is not valid.");
        }
        
        if (parameters.containsKey("introduced")) {
            paramValue = parameters.get("introduced")[0];
            if (paramValue != null && !paramValue.isEmpty()) {
                if (checkDate(paramValue)) {
                    i = LocalDate.parse(paramValue);
                } else {
                    throw new ServletException("Sorry, the date of introduction is not valid : " + paramValue);
                }
            }
        } else {
            throw new ServletException("Sorry, the computer is not valid.");
        }
        
        if (parameters.containsKey("discontinued")) {
            paramValue = parameters.get("discontinued")[0];
            if (paramValue != null && !paramValue.isEmpty()) {
                if (checkDate(paramValue)) {
                    d = LocalDate.parse(paramValue);
                } else {
                    throw new ServletException("Sorry, the date of introduction is not valid : " + paramValue);
                }
            }
        } else {
            throw new ServletException("Sorry, the computer is not valid.");
        }
        
        if (i != null && d != null) {
            if (i.isBefore(d)) {
                computer.setIntroduced(i);
                computer.setDiscontinued(d);
            } else {
                throw new ServletException("Sorry, there is a problem with dates is not valid : " + i + " " + d);
            }
        }
        
        return computer;
    }
}
