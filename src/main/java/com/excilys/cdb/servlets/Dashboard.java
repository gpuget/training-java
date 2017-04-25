package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.models.Page;
import com.excilys.cdb.services.ComputerService;

public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = -4333507256112472526L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        Map<String, String[]> parameters = req.getParameterMap();
        int numberPage = 1;
        int maxPerPage = 10;
        int count;
        Page<ComputerDTO> page;

        // Number of page
        if (parameters.containsKey("page") && parameters.get("page")[0].matches("[1-9][0-9]*")) {
            numberPage = Integer.parseInt(parameters.get("page")[0]);
        }
        
        // Max per page
        if (parameters.containsKey("max") && parameters.get("max")[0].matches("[1-9]+0")) {
            maxPerPage = Integer.parseInt(parameters.get("max")[0]);
        }
        
        // Search
        if (parameters.containsKey("search") && !parameters.get("search")[0].isEmpty()) {
            page = getFilteredByNamePage(numberPage, maxPerPage, parameters.get("search")[0]);
            count = getFilteredByNameCount(parameters.get("search")[0]);
        } else {
            page = getPage(numberPage, maxPerPage);
            count = getCount();
        }
        
        req.setAttribute("pageComputer", page);
        req.setAttribute("count", count);
        
        this.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }
    
    private Page<ComputerDTO> getFilteredByNamePage(int number, int maxPerPage, String name){
        return ComputerMapper.toComputerDTO(new ComputerService().getFilteredByNamePage(number, maxPerPage, name));
    }
    
    private int getFilteredByNameCount(String name){
        return new ComputerService().getFilteredByNameCount(name);
    }
    
    private Page<ComputerDTO> getPage(int number, int maxPerPage){
        return ComputerMapper.toComputerDTO(new ComputerService().getPage(number, maxPerPage));
    }
    
    private int getCount(){
        return new ComputerService().getCount();
    }
}
