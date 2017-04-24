package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.models.Computer;
import com.excilys.cdb.models.Page;
import com.excilys.cdb.services.ComputerService;

public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = -4333507256112472526L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String[]> parameters = req.getParameterMap();        
        ComputerService cs = new ComputerService();
        int numberPage = 1;
        int maxPerPage = 10;
        int count;
        Page<Computer> page;

        // Number of page
        if (parameters.containsKey("page") && parameters.get("page")[0].matches("[1-9]+")) {
            numberPage = Integer.parseInt(parameters.get("page")[0]);
        }
        
        // Search
        if (parameters.containsKey("search") && !parameters.get("search")[0].isEmpty()) {
            page = cs.getFilteredByNamePage(numberPage, maxPerPage, parameters.get("search")[0]);
            count = cs.getFilteredByNameCount(parameters.get("search")[0]);
        } else {
            page = cs.getPage(numberPage, maxPerPage);
            count = cs.getCount();
        }
        
        req.setAttribute("pageComputer", page);
        req.setAttribute("count", count);
        
        this.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
    }

}
