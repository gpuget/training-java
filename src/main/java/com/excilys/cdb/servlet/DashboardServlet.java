package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.dto.ComputerDTO;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.StringValidator;

public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = -4333507256112472526L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.trace("dashboard GET");
        Map<String, String[]> parameters = req.getParameterMap();
        int numberPage = 1;
        int maxPerPage = 10;
        LOGGER.debug("Default number page and max : " + numberPage + ' ' + maxPerPage);
        String search = null;
        String paramValue;
        Page<ComputerDTO> page;

        // Number of page
        LOGGER.debug("Servlet parameter page");
        if (parameters.containsKey("page")) {
            paramValue = parameters.get("page")[0];
            if (StringValidator.checkIsNumber(paramValue)) {
                numberPage = Integer.parseInt(paramValue);
                LOGGER.debug("New number page : " + numberPage);
            }
        }

        // Max per page
        LOGGER.debug("Servlet parameter max");
        if (parameters.containsKey("max")) {
            paramValue = parameters.get("max")[0];
            if (StringValidator.checkIsNumber(paramValue)) {
                maxPerPage = Integer.parseInt(paramValue);
                LOGGER.debug("New number max : " + maxPerPage);
            }
        }

        // Search
        LOGGER.debug("Servlet parameter search");
        if (parameters.containsKey("search")) {
            search = parameters.get("search")[0];
            if (StringValidator.checkNoSpecialsChars(search)) {
                LOGGER.debug("Filtered page returned");
                page = getFilteredByNamePage(numberPage, maxPerPage, search);
            } else {
                String message = "Sorry, the search value is not allowed.";
                LOGGER.error(message);
                throw new ServletException(message);
            }
        } else {
            LOGGER.debug("Default page returned");
            page = getPage(numberPage, maxPerPage);
        }

        int count = getCount();
        LOGGER.debug("Set attribute pageComputer : " + page);
        req.setAttribute("pageComputer", page);
        LOGGER.debug("Set attribute count : " + count);
        req.setAttribute("count", count);
        LOGGER.debug("Set attribute search : " + search);
        req.setAttribute("search", search);

        LOGGER.debug("Set request dispatcher /addComputer.jsp");
        this.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.trace("dashboard POST");
        Map<String, String[]> parameters = req.getParameterMap();

        LOGGER.debug("Servlet parameter selection");
        if (parameters.containsKey("selection")) {
            deleteList(ComputerValidator.getValidIdList(parameters.get("selection")[0]));
        }

        LOGGER.debug("Redirect dashboard");
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
        LOGGER.trace("Get filtered by name page : (" + name + ") number" + number + "with max " + maxPerPage);
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
        LOGGER.trace("Get filtered by name page : number" + number + "with max " + maxPerPage);
        return ComputerMapper.toComputerDTO(ComputerService.INSTANCE.getPage(number, maxPerPage));
    }

    /**
     * Gets the total number of computers.
     *
     * @return total number of computers
     */
    private int getCount() {
        LOGGER.trace("Count of computers");
        return ComputerService.INSTANCE.getCount();
    }

    /**
     * Deletes the computers corresponding to the identifiers.
     *
     * @param idsList identifiers
     */
    private void deleteList(List<Long> idsList) {
        LOGGER.trace("Delete list : " + idsList);
        ComputerService.INSTANCE.deleteList(idsList);
    }
}
