package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.models.Company;
import com.excilys.cdb.models.Computer;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.validators.ComputerValidator;

public class EditComputer extends HttpServlet {
    private static final long serialVersionUID = 4989886087572935146L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        
        if (id != null && ComputerValidator.checkId(id)) {            
            req.setAttribute("companies", CompanyMapper.toCompanyDTO(new CompanyService().getCompanies()));        
            req.setAttribute("computer", ComputerMapper.toComputerDTO(new ComputerService().getDetails(Integer.parseInt(id))));
            
            this.getServletContext().getRequestDispatcher("/editComputer.jsp").forward(req, resp);
        } else {
            throw new ServletException("Sorry, an error has occured.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String[]> parameters = req.getParameterMap();
        ComputerService cs = new ComputerService();
        Company company;
        Computer computer;
        
        // CompanyId
        if (parameters.containsKey("companyId")) {
            company = ComputerValidator.getValidCompany(parameters.get("companyId")[0]);
        } else {
            throw new ServletException("Sorry, the computer is not valid.");
        }      
        
        // ComputerName, introduced, discontinued
        computer = ComputerValidator.getValidComputer(parameters);
        computer.setManufacturer(company);

        // ComputerId
        if (parameters.containsKey("computerId")) {
            String id = parameters.get("computerId")[0];
            if (!id.isEmpty() && ComputerValidator.checkId(id)) {
                computer.setId(Integer.parseInt(id));
            } else {
                throw new ServletException("Sorry, the computer is not valid.");
            }
        } else {
            throw new ServletException("Sorry, the computer is not valid.");
        }  
        try {
            cs.update(computer);
            resp.sendRedirect("dashboard");
        } catch (Exception e) {
            resp.sendError(500);
            throw new ServletException("Sorry, an error has occured.", e);
        }
    }
}