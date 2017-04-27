package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.models.Page;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.validators.ComputerValidator;
import com.excilys.cdb.validators.StringValidator;

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
        String paramValue;
        Page<ComputerDTO> page;

        // Number of page
        if (parameters.containsKey("page")) {
            paramValue = parameters.get("page")[0];
            if (paramValue != null && StringValidator.checkIsNumber(paramValue)) {
                numberPage = Integer.parseInt(paramValue);
            }
        }
        
        // Max per page
        if (parameters.containsKey("pageSize")) {
            paramValue = parameters.get("pageSize")[0];
            if (paramValue != null && StringValidator.checkIsNumber(paramValue)) {
                maxPerPage = Integer.parseInt(paramValue);
            }
        }
        
        // Search
        if (parameters.containsKey("search")) {
            paramValue = parameters.get("search")[0];
            if (StringValidator.checkNoSpecialsChars(paramValue)) {
                page = getFilteredByNamePage(numberPage, maxPerPage, paramValue);
                count = getFilteredByNameCount(paramValue);
            } else {
                throw new ServletException("Sorry, the search value is not allowed.");
            }
        } else {            
            page = getPage(numberPage, maxPerPage);
            count = getCount();
        }
        
        req.setAttribute("pageComputer", page);
        req.setAttribute("count", count);
        
        this.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        Map<String, String[]> parameters = req.getParameterMap();
        
        if (parameters.containsKey("selection")) {
            deleteList(ComputerValidator.getValidIdList(parameters.get("selection")[0]));
        }
        
        resp.sendRedirect("dashboard");
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
    
    private void deleteList(List<Long> idsList) {
        new ComputerService().deleteList(idsList);
    }
}
