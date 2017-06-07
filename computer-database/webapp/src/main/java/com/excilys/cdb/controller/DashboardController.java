package com.excilys.cdb.controller;

import java.util.ArrayList;
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

            if (result.getFieldError("search") != null) {
                LOGGER.warn("The search is not valid.");
                params.setSearch(null);
            }

            if (result.getFieldError("column") != null) {
                LOGGER.warn("The column is not valid.");
                params.setColumn(null);
            }

            if (result.getFieldError("order") != null) {
                LOGGER.warn("The order is not valid.");
                params.setOrder(0);
            }
        }

        Page<ComputerDTO> page;
        String column = (params.getColumn() == null ? "id" : params.getColumn());
        if (params.getSearch() != null) {
            LOGGER.debug("Filtered page returned");
            page = getFilteredByNamePage(params.getPage(), params.getMax(), column, params.getOrder(), params.getSearch());

            LOGGER.debug("Set attribute search : " + params.getSearch());
            model.addAttribute("search", params.getSearch());
        } else {
            LOGGER.debug("Default page returned");
            page = getPage(params.getPage(), params.getMax(), column, params.getOrder());
        }

        LOGGER.debug("Set attribute pageComputer : " + page);
        model.addAttribute("pageComputer", page);
        LOGGER.debug("Set attribute column : " + column);
        model.addAttribute("column", column);
        LOGGER.debug("Set attribute order : " + params.getOrder());
        model.addAttribute("order", params.getOrder());

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

        deleteList(getValidIdList(selection));

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
    private Page<ComputerDTO> getFilteredByNamePage(int number, int maxPerPage, String column, int order, String name) {
        LOGGER.info("Get filtered by name page : (" + name + ") number " + number + " with max "
                + maxPerPage);
        return computerService.getFilteredByNamePage(number, maxPerPage, column, order, name);
    }

    /**
     * Gets the page of computers.
     *
     * @param number number of the page
     * @param maxPerPage maximum number of items
     * @return page page of computers
     */
    private Page<ComputerDTO> getPage(int number, int maxPerPage, String column, int order) {
        LOGGER.info("Get page : number " + number + " with max " + maxPerPage);
        return computerService.getPage(number, maxPerPage, column, order);
    }

    /**
     * Returns a valid list of identifiers.
     *
     * @param ids list of identifiers to check
     * @return list of identifiers
     * @throws ControllerException if the selection is not validate
     */
    private List<Long> getValidIdList(String ids) throws ControllerException {
        LOGGER.info("Get valid list of id : " + ids);
        List<Long> idsList = new ArrayList<>();
        String[] idsTab = ids.split(",");
        for (String id : idsTab) {
            if (id != null && id.matches(RequestParameters.SEARCH_REGEX)) {
                idsList.add(Long.parseLong(id));
            }
        }

        if (idsList.isEmpty()) {
            String message = "Sorry, the selection is not valid.";
            LOGGER.error(message);
            throw new ControllerException(message);
        }

        return idsList;
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