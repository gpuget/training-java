package com.excilys.cdb.controller.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.model.dto.CompanyDTO;
import com.excilys.cdb.service.CompanyService;

@RestController
@RequestMapping(RequestUri.ROOT)
public class CompanyRestController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CompanyRestController.class);

    @Autowired
    private CompanyService companyService;

    @GetMapping(value = RequestUri.GET_ALL_COMPANIES, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<CompanyDTO> getCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping(value = RequestUri.GET_COMPANY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody CompanyDTO getCompany(@PathVariable long id) {
        return companyService.getCompanyById(id);
    }
}
