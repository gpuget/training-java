package com.excilys.cdb.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.dto.ComputerDTO;
import com.excilys.cdb.service.ComputerService;

@RestController
@RequestMapping(RequestUri.ROOT)
public class ComputerRestController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ComputerRestController.class);

    @Autowired
    private ComputerService computerService;

    @GetMapping(value = RequestUri.GET_ALL_COMPUTERS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<ComputerDTO> getComputers() {
        LOGGER.info("Rest Computer GET ALL");
        return computerService.getComputers();
    }

    @GetMapping(value = RequestUri.GET_COMPUTER, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ComputerDTO getComputer(@PathVariable long id) {
        LOGGER.info("Rest Computer GET ID");
        return computerService.getComputerById(id);
    }

    @PostMapping(RequestUri.CREATE_COMPUTER)
    public ResponseEntity<String> createComputer(@Valid @RequestBody ComputerDTO computerDto,
            BindingResult results) {
        LOGGER.info("Rest Computer POST");
        LOGGER.debug("Posted ComputerDTO : " + computerDto);

        if (!results.hasErrors()) {
            try {
                computerService.create(computerDto);
                return new ResponseEntity<>("Computer has been created", HttpStatus.ACCEPTED);
            } catch (DAOException e) {
                String message = "Sorry, an error has occured during the computer creation.";
                LOGGER.error(message);
                return new ResponseEntity<>("Computer has not been created", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            LOGGER.warn(results.getAllErrors().toString());
            return new ResponseEntity<>("Computer is not valid", HttpStatus.BAD_REQUEST);
        }
    }
}
