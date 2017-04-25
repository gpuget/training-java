package com.excilys.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.models.Company;
import com.excilys.cdb.models.Computer;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.validators.ComputerValidator;
import com.excilys.cdb.validators.StringValidator;

public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 4989886087572935146L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CompanyService coms = new CompanyService();
        
        req.setAttribute("companies", CompanyMapper.toCompanyDTO(coms.getCompanies()));
        
        this.getServletContext().getRequestDispatcher("/addComputer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        Map<String, String[]> parameters = req.getParameterMap();
        ComputerService cs = new ComputerService();
        Company company;
        Computer computer;
        
        // CompanyId
        if (parameters.containsKey("companyId")) {
            company = getValidCompany(parameters.get("companyId")[0]);
        } else {
            throw new ServletException("Sorry, the computer is not valid.");
        }
        
        // ComputerName, introduced, discontinued
        computer = getValidComputer(parameters);
        computer.setManufacturer(company);
        
        try {
            cs.create(computer);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }
    
    private Company getValidCompany(String companyId) throws ServletException {
        Company company;
        
        if (!companyId.isEmpty() && StringValidator.checkIsNumber(companyId)) {
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
    
    private Computer getValidComputer(Map<String, String[]> parameters) throws ServletException {
        String paramValue;
        Computer computer = new Computer.Builder().build();
        LocalDate i = null;
        LocalDate d = null;
        
        if (parameters.containsKey("companyName")) {
            paramValue = parameters.get("computerName")[0];
            if (!paramValue.isEmpty() && ComputerValidator.checkName(paramValue)) {
                computer.setName(paramValue);
            } else {
                throw new ServletException("Sorry, the computer name is not valid : " + paramValue);
            }
        }
        
        if (parameters.containsKey("introduced")) {
            paramValue = parameters.get("introduced")[0];
            if (ComputerValidator.checkDate(paramValue)) {
                i = LocalDate.parse(paramValue);
            } else {
                throw new ServletException("Sorry, the date of introduction is not valid : " + paramValue);
            }
        }
        
        if (parameters.containsKey("discontinued")) {
            paramValue = parameters.get("discontinued")[0];
            if (ComputerValidator.checkDate(paramValue)) {
                d = LocalDate.parse(paramValue);
            } else {
                throw new ServletException("Sorry, the date of discontinue is not valid: " + paramValue);
            }
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
