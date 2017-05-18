package com.excilys.cdb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.exception.ControllerException;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.mapper.dto.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.model.dto.ComputerDTO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AddComputerController.class);

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public String get(ModelMap model) {
        LOGGER.info("addComputer GET");
        LOGGER.debug("Company service : " + companyService);
        LOGGER.debug("Computer service : " + computerService);

        List<CompanyDTO> companies = CompanyMapper.toCompanyDTO(companyService.getCompanies());
        LOGGER.debug("Set attribute compagnies : " + companies);
        model.addAttribute("companies", companies);

        LOGGER.debug("Dispatcher : addComputer");
        return "addComputer";
    }

    @PostMapping
    public String post(@ModelAttribute ComputerDTO computerDto, HttpServletResponse resp) throws IOException {
        LOGGER.info("addComputer POST");
        LOGGER.debug(computerDto.toString());

        Company company;
        String companyId = computerDto.getCompanyId();
        if (ComputerValidator.checkId(companyId)) {
            LOGGER.debug("Long parse id : " + companyId);
            company = companyService.getCompanyById(Long.parseLong(companyId));
        } else {
            String message = "Sorry, the company id is not valid.";
            LOGGER.error(message);
            throw new ControllerException(message);
        }
        LOGGER.debug("Valid company : " + company);

        Computer computer;
        LOGGER.debug("Servlet parameter computerName, introduced and discontinued");
        computer = ComputerValidator.getValidComputer(computerDto.getName(), computerDto.getIntroduced(), computerDto.getDiscontinued());
        computer.setManufacturer(company);
        LOGGER.debug("Valid computer : " + computer);

        try {
            computerService.create(computer);
            LOGGER.debug("Dispatcher : redirect:/dashboard");
            return "redirect:/dashboard";
        } catch (DAOException e) {
            String message = "Sorry, an error has occured during the computer creation.";
            LOGGER.error(message);
            resp.sendError(500);
            throw new ControllerException(message, e);
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