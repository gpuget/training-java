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
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.controller.dto.RequestParameters;
import com.excilys.cdb.exception.ControllerException;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.dto.ComputerDTO;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.StringValidator;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    public static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ComputerService computerService;

    /**
     * Get method for dashboard.
     *
     * @param model model
     * @param params request parameters
     * @param result validation result
     * @return servlet mapping
     */
    @GetMapping
    public String get(ModelMap model, @Valid @ModelAttribute RequestParameters params,
            BindingResult result) {
        LOGGER.info("dashboard GET");
        LOGGER.debug("Computer service : " + computerService);
        LOGGER.debug("Request params : " + params);

        if (result.hasErrors()) {
            if (result.getFieldError("page") != null) {
                LOGGER.warn("The number page was not valid : reset to 1");
                params.setPage(1);
            }

            if (result.getFieldError("max") != null) {
                LOGGER.warn("The max per page was not valid : reset to 10");
                params.setMax(10);
            }
        }

        Page<ComputerDTO> page;
        if (params.getSearch() != null && !params.getSearch().isEmpty()) {
            if (StringValidator.checkNoSpecialsChars(params.getSearch())) {
                LOGGER.debug("Filtered page returned");
                page = getFilteredByNamePage(params.getPage(), params.getMax(), params.getSearch());

                LOGGER.debug("Set attribute search : " + params.getSearch());
                model.addAttribute("search", params.getSearch());
            } else {
                String message = "Sorry, the search value is not allowed.";
                LOGGER.error(message);
                throw new ControllerException(message);
            }
        } else {
            LOGGER.debug("Default page returned");
            page = getPage(params.getPage(), params.getMax());
        }

        int count = getCount();
        LOGGER.debug("Set attribute pageComputer : " + page);
        model.addAttribute("pageComputer", page);
        LOGGER.debug("Set attribute count : " + count);
        model.addAttribute("count", count);

        LOGGER.debug("Dispatcher : dashboard");
        return "dashboard";
    }

    /**
     * Post method for dashboard.
     *
     * @param selection slection to delete
     * @return sevlet mapping
     */
    @PostMapping
    public String post(@RequestParam(name = "selection", required = false) String selection) {
        LOGGER.info("dashboard GET");
        LOGGER.debug("Computer service : " + computerService);
        LOGGER.debug("Selection : " + selection);

        deleteList(ComputerValidator.getValidIdList(selection));

        LOGGER.debug("Dispatcher : redirect:/dashboard");
        return "redirect:/dashboard";
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
        LOGGER.info("Get filtered by name page : (" + name + ") number " + number + " with max "
                + maxPerPage);
        return computerService.getFilteredByNamePage(number, maxPerPage, name);
    }

    /**
     * Gets the page of computers.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @return page page of computers
     */
    private Page<ComputerDTO> getPage(int number, int maxPerPage) {
        LOGGER.info("Get page : number " + number + " with max " + maxPerPage);
        return computerService.getPage(number, maxPerPage);
    }

    /**
     * Gets the total number of computers.
     *
     * @return total number of computers
     */
    private int getCount() {
        LOGGER.info("Count of computers");
        return computerService.getCount();
    }

    /**
     * Deletes the computers corresponding to the identifiers.
     *
     * @param idsList identifiers
     */
    private void deleteList(List<Long> idsList) {
        LOGGER.info("Delete list : " + idsList);
        computerService.deleteList(idsList);
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
}