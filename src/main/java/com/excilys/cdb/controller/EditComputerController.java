package com.excilys.cdb.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.exception.ControllerException;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.mapper.dto.CompanyMapper;
import com.excilys.cdb.mapper.dto.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.model.dto.ComputerDTO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;

@Controller
@RequestMapping("/editComputer")
public class EditComputerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(EditComputerController.class);

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public String get(ModelMap model, @RequestParam(name = "id", required = true) String id) {
        LOGGER.info("editComputer GET");
        LOGGER.debug("Company service : " + companyService);
        LOGGER.debug("Computer service : " + computerService);

        List<CompanyDTO> companiesDto = CompanyMapper.toCompanyDTO(companyService.getCompanies());
        ComputerDTO computerDto = ComputerMapper
                .toComputerDTO(computerService.getDetails(Long.parseLong(id)));

        if (ComputerValidator.checkId(id)) {
            LOGGER.debug("Set attribute companies : " + companiesDto);
            model.addAttribute("companies", companiesDto);
            LOGGER.debug("Set attribute companies : " + computerDto);
            model.addAttribute("computer", computerDto);

            LOGGER.debug("Dispatcher : editComputer");
            return "editComputer";
        } else {
            String message = "Sorry, the computer is not valid.";
            LOGGER.error(message);
            throw new ControllerException(message);
        }
    }

    @PostMapping
    public String post(@RequestParam(name = "companyId", required = true) String companyId,
            @RequestParam(name = "computerName", required = true) String computerName,
            @RequestParam(name = "introduced", required = false) String introduced,
            @RequestParam(name = "discontinued", required = false) String discontinued) {
        LOGGER.info("editComputer POST");

        Company company;
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
        computer = ComputerValidator.getValidComputer(computerName, introduced, discontinued);
        computer.setManufacturer(company);
        LOGGER.debug("Valid computer : " + computer);

        try {
            computerService.update(computer);
            LOGGER.debug("Dispatcher : redirect:/dashboard");
            return "redirect:/dashboard";
        } catch (DAOException e) {
            String message = "Sorry, an error has occured during the computer creation.";
            LOGGER.error(message);
            // resp.sendRoor(500);
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