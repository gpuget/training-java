package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.services.CompanyService;

public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 4989886087572935146L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CompanyService cs = new CompanyService();
        
        req.setAttribute("companies", cs.getPage().getObjects());
        
        this.getServletContext().getRequestDispatcher("/addComputer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String[]> parameters = req.getParameterMap();
        
        this.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }
}
