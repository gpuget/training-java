package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;

public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 4989886087572935146L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        req.setAttribute("companies", CompanyMapper.toCompanyDTO(new CompanyService().getCompanies()));
        
        this.getServletContext().getRequestDispatcher("/addComputer.jsp").forward(req, resp);
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
        
        try {
            cs.create(computer);
            resp.sendRedirect("dashboard");
        } catch (Exception e) {
            resp.sendError(500);
            throw new ServletException("Sorry, an error has occured.", e);
        }
    }
}
