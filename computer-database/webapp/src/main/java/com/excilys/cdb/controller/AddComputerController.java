package com.excilys.cdb.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.exception.ControllerException;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.model.dto.ComputerDTO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AddComputerController.class);

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    /**
     * Get method for computer creation servlet.
     *
     * @param model model of the result page
     * @return servlet mapping
     */
    @GetMapping
    public String get(ModelMap model) {
        LOGGER.info("addComputer GET");
        LOGGER.debug("Company service : " + companyService);
        LOGGER.debug("Computer service : " + computerService);

        List<CompanyDTO> companies = companyService.getCompanies();
        LOGGER.debug("Set attribute compagnies : " + companies);
        model.addAttribute("companies", companies);

        LOGGER.debug("Dispatcher : addComputer");
        return "addComputer";
    }

    /**
     * Post method for computer creation servlet.
     *
     * @param model model
     * @param computerDto posted computer
     * @param results validation results
     * @return servlet mapping
     */
    @PostMapping
    public String post(ModelMap model, @Valid @ModelAttribute ComputerDTO computerDto,
            BindingResult results) {
        LOGGER.info("addComputer POST");
        LOGGER.debug("Posted ComputerDTO : " + computerDto);

        if (!results.hasErrors()) {
            try {
                computerService.create(computerDto);
                LOGGER.debug("Dispatcher : redirect:/dashboard");
                return "redirect:/dashboard";
            } catch (DAOException e) {
                String message = "Sorry, an error has occured during the computer creation.";
                LOGGER.error(message);
                throw new ControllerException(message, e);
            }
        } else {
            List<CompanyDTO> companies = companyService.getCompanies();
            LOGGER.debug("Set attribute compagnies : " + companies);
            model.addAttribute("companies", companies);
            LOGGER.warn(results.getAllErrors().toString());
            model.addAttribute("errors", results.getAllErrors());
            return "addComputer";
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