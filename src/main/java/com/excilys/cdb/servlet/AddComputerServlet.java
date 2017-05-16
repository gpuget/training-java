package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.mapper.dto.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;

public class AddComputerServlet extends MyServlet {
    private static final long serialVersionUID = 4989886087572935146L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerServlet.class);

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("addComputer GET");
        LOGGER.debug("Company service : " + companyService);
        LOGGER.debug("Computer service : " + computerService);

        List<CompanyDTO> companies = CompanyMapper.toCompanyDTO(companyService.getCompanies());
        LOGGER.debug("Set attribute compagnies : " + companies);
        req.setAttribute("companies", companies);

        LOGGER.debug("Set request dispatcher /addComputer.jsp");
        this.getServletContext().getRequestDispatcher("/addComputer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("addComputer POST");
        Map<String, String[]> parameters = req.getParameterMap();
        Company company;
        Computer computer;

        // CompanyId
        LOGGER.debug("Servlet parameter companyId");
        if (parameters.containsKey("companyId")) {
            String companyId = parameters.get("companyId")[0];
            if (ComputerValidator.checkId(companyId)) {
                LOGGER.debug("Long parse id : " + companyId);
                company = companyService.getCompanyById(Long.parseLong(companyId));
                if (company == null) {
                    String message = "Sorry, the company does not exist.";
                    LOGGER.error(message);
                    throw new ServletException(message);
                }
            } else {
                String message = "Sorry, the computer is not valid.";
                LOGGER.error(message);
                throw new ServletException(message);
            }
            LOGGER.debug("Valid company : " + company);
        } else {
            String message = "Sorry, the company id is not valid.";
            LOGGER.error(message);
            throw new ServletException(message);
        }

        // ComputerName, introduced, discontinued
        LOGGER.debug("Servlet parameter computerName, introduced and discontinued");
        computer = ComputerValidator.getValidComputer(parameters);
        computer.setManufacturer(company);
        LOGGER.debug("Valid computer : " + computer);

        try {
            computerService.create(computer);
            LOGGER.debug("Redirect to dashboard servlet");
            resp.sendRedirect("dashboard");
        } catch (Exception e) {
            String message = "Sorry, an error has occured during the computer creation.";
            LOGGER.error(message);
            resp.sendError(500);
            throw new ServletException(message, e);
        }
    }

    /**
     * Sets the computer service.
     *
     * @param computerService computer service to use
     */
    public void setComputerService(ComputerService computerService) {
        LOGGER.info("Set computer service : " + computerService);
        this.computerService = computerService;
    }

    /**
     * Sets the company service.
     *
     * @param companyService company service to use
     */
    public void setCompanyService(CompanyService companyService) {
        LOGGER.info("Set company service : " + companyService);
        this.companyService = companyService;
    }
}
