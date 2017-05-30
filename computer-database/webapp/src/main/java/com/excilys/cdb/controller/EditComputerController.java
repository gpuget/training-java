package com.excilys.cdb.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.exception.ControllerException;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.model.dto.ComputerDTO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/editComputer")
public class EditComputerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(EditComputerController.class);

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    /**
     * Get method for computer edition.
     *
     * @param model model
     * @param id selected computer
     * @return servlet mapping
     */
    @GetMapping
    public String get(ModelMap model, @Min(1) @RequestParam(name = "id", required = true) String id) {
        LOGGER.info("editComputer GET");
        LOGGER.debug("Company service : " + companyService);
        LOGGER.debug("Computer service : " + computerService);

        List<CompanyDTO> companiesDto = companyService.getCompanies();
        ComputerDTO computerDto = computerService.getDetails(Long.parseLong(id));
        LOGGER.debug("Set attribute companies : " + companiesDto);
        model.addAttribute("companies", companiesDto);
        LOGGER.debug("Set attribute computer : " + computerDto);
        model.addAttribute("computer", computerDto);

        LOGGER.debug("Dispatcher : editComputer");
        return "editComputer";
    }

    /**
     * Post method for computer edition.
     *
     * @param model model
     * @param computerDto posted computer
     * @param results validation result
     * @return servlet mapping
     */
    @PostMapping
    public String post(ModelMap model, @Valid @ModelAttribute ComputerDTO computerDto, BindingResult results) {
        LOGGER.info("editComputer POST");
        LOGGER.debug("Posted ComputerDTO : " + computerDto);
        LOGGER.debug("Validation results : " + results);
        String message = "Sorry, an error has occured during the computer creation.";

        if (!results.hasErrors()) {
            try {
                computerService.update(computerDto);
                LOGGER.debug("Dispatcher : redirect:/dashboard");
                return "redirect:/dashboard";
            } catch (DAOException e) {
                LOGGER.error(message);
                throw new ControllerException(message, e);
            }
        } else {
            LOGGER.warn(results.getAllErrors().toString());
            model.addAttribute("errors", results.getAllErrors());
            return "/editComputer";
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