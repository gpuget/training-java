package com.excilys.cdb.controller.rest;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.exception.DAOException;
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
        LOGGER.info("Rest Company GET ALL");
        return companyService.getCompanies();
    }

    @GetMapping(value = RequestUri.GET_COMPANY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody CompanyDTO getCompany(@Min(1) @PathVariable long id) {
        LOGGER.info("Rest Company GET : " + id);
        return companyService.getCompanyById(id);
    }

    @PostMapping(RequestUri.CREATE_COMPANY)
    public ResponseEntity<String> createComputer(@NotNull @RequestBody CompanyDTO companyDto) {
        LOGGER.info("Rest Company POST");
        LOGGER.debug("Posted ComputerDTO : " + companyDto);
        try {
            companyService.create(companyDto);
            return new ResponseEntity<>("Company has been created", HttpStatus.ACCEPTED);
        } catch (DAOException e) {
            String message = "Sorry, an error has occured during the company creation.";
            LOGGER.error(message);
            return new ResponseEntity<>("Company has not been created",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(RequestUri.UPDATE_COMPANY)
    public ResponseEntity<String> updateCompany(@NotNull @RequestBody CompanyDTO companyDto) {
        LOGGER.info("Rest Company PUT");
        LOGGER.debug("Posted ComputerDTO : " + companyDto);
        try {
            companyService.update(companyDto);
            return new ResponseEntity<>("Company has been modified", HttpStatus.ACCEPTED);
        } catch (DAOException e) {
            String message = "Sorry, an error has occured during the company creation.";
            LOGGER.error(message);
            return new ResponseEntity<>("Company has not been modified",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(RequestUri.DELETE_COMPANY)
    public ResponseEntity<String> deleteComputer(@Min(1) @PathVariable long id) {
        LOGGER.info("Rest Comapny DELETE : " + id);
        try {
            companyService.delete(id);
            return new ResponseEntity<>("Company has been deleted", HttpStatus.ACCEPTED);
        } catch (DAOException e) {
            String message = "Sorry, an error has occured during the company creation.";
            LOGGER.error(message);
            return new ResponseEntity<>("Company has not been deleted",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
