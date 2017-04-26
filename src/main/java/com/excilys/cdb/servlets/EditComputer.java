package com.excilys.cdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.services.CompanyService;

public class EditComputer extends HttpServlet {
    private static final long serialVersionUID = 4989886087572935146L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        
        if (id == null || id.isEmpty()) {
            resp.sendRedirect("dashboard");
        } else {
            
            req.setAttribute("companies", CompanyMapper.toCompanyDTO(new CompanyService().getCompanies()));        
            req.setAttribute("computer", null);
            
            this.getServletContext().getRequestDispatcher("/editComputer.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
    }
}