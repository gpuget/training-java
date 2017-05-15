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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.model.dto.ComputerDTO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;

public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 4989886087572935146L;
    private static final Logger LOGGER = LoggerFactory.getLogger(EditComputerServlet.class);

    ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/service/computerService.xml", "spring/service/companyService.xml"});
    private ComputerService computerService = (ComputerService) context.getBean("computerService");
    private CompanyService companyService = (CompanyService) context.getBean("companyService");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.trace("editComputer GET");
        LOGGER.debug("Servlet parameter id");
        String id = req.getParameter("id");
        List<CompanyDTO> companiesDto = CompanyMapper
                .toCompanyDTO(companyService.getCompanies());
        ComputerDTO computerDto = ComputerMapper
                .toComputerDTO(computerService.getDetails(Long.parseLong(id)));

        if (ComputerValidator.checkId(id)) {
            LOGGER.debug("Set attribute companies : " + companiesDto);
            req.setAttribute("companies", companiesDto);
            LOGGER.debug("Set attribute companies : " + computerDto);
            req.setAttribute("computer", computerDto);

            LOGGER.debug("Request dispatcher editComputer");
            this.getServletContext().getRequestDispatcher("/editComputer.jsp").forward(req, resp);
        } else {
            String message = "Sorry, the computer is not valid.";
            LOGGER.error(message);
            throw new ServletException(message);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.trace("editComputer POST");
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
        LOGGER.debug("Servlet parameter computerName, introduced, discontinued");
        computer = ComputerValidator.getValidComputer(parameters);
        computer.setManufacturer(company);
        LOGGER.debug("Valid computer : " + computer);

        // ComputerId
        LOGGER.debug("Servlet parameter computerId");
        String errorMessage = "Sorry, the computer is not valid.";
        if (parameters.containsKey("computerId")) {
            String id = parameters.get("computerId")[0];
            if (ComputerValidator.checkId(id)) {
                computer.setId(Integer.parseInt(id));
                LOGGER.debug("Valid computer id : " + id);
            } else {
                LOGGER.error(errorMessage);
                throw new ServletException(errorMessage);
            }
        } else {
            LOGGER.error(errorMessage);
            throw new ServletException(errorMessage);
        }

        try {
            computerService.update(computer);
            LOGGER.debug("Redirect dashboard");
            resp.sendRedirect("dashboard");
        } catch (Exception e) {
            String message = "Sorry, an error has occured during the computer update.";
            LOGGER.error(message);
            resp.sendError(500);
            throw new ServletException(message, e);
        }
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setComputerService(ComputerService computerService) {
        this.computerService = computerService;
    }
}