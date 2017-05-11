package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.dto.ComputerDTO;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.StringValidator;

public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = -4333507256112472526L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String[]> parameters = req.getParameterMap();
        int numberPage = 1;
        int maxPerPage = 10;
        String search = null;
        String paramValue;
        Page<ComputerDTO> page;

        // Number of page
        if (parameters.containsKey("page")) {
            paramValue = parameters.get("page")[0];
            if (StringValidator.checkIsNumber(paramValue)) {
                numberPage = Integer.parseInt(paramValue);
            }
        }

        // Max per page
        if (parameters.containsKey("max")) {
            paramValue = parameters.get("max")[0];
            if (StringValidator.checkIsNumber(paramValue)) {
                maxPerPage = Integer.parseInt(paramValue);
            }
        }

        // Search
        if (parameters.containsKey("search")) {
            search = parameters.get("search")[0];
            if (StringValidator.checkNoSpecialsChars(search)) {
                page = getFilteredByNamePage(numberPage, maxPerPage, search);
            } else {
                throw new ServletException("Sorry, the search value is not allowed.");
            }
        } else {
            page = getPage(numberPage, maxPerPage);
        }

        req.setAttribute("pageComputer", page);
        req.setAttribute("count", getCount());
        req.setAttribute("search", search);

        this.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String[]> parameters = req.getParameterMap();

        if (parameters.containsKey("selection")) {
            deleteList(ComputerValidator.getValidIdList(parameters.get("selection")[0]));
        }

        resp.sendRedirect("dashboard");
    }

    /**
     * Gets the pages of computers corresponding to specified name.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @param name seek name
     * @return page of filtered computers
     */
    private Page<ComputerDTO> getFilteredByNamePage(int number, int maxPerPage, String name) {
        return ComputerMapper.toComputerDTO(
                ComputerService.INSTANCE.getFilteredByNamePage(number, maxPerPage, name));
    }

    /**
     * Gets the page of computers.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @return page page of computers
     */
    private Page<ComputerDTO> getPage(int number, int maxPerPage) {
        return ComputerMapper.toComputerDTO(ComputerService.INSTANCE.getPage(number, maxPerPage));
    }

    /**
     * Gets the total number of computers.
     *
     * @return total number of computers
     */
    private int getCount() {
        return ComputerService.INSTANCE.getCount();
    }

    /**
     * Deletes the computers corresponding to the identifiers.
     *
     * @param idsList identifiers
     */
    private void deleteList(List<Long> idsList) {
        ComputerService.INSTANCE.deleteList(idsList);
    }
}
