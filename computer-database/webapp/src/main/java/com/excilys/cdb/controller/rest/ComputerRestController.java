package com.excilys.cdb.controller.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
        return computerService.getComputers();
    }

    @GetMapping(value = RequestUri.GET_COMPUTER, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ComputerDTO getComputer(@PathVariable long id) {
        return computerService.getComputerById(id);
    }

    @PostMapping(RequestUri.CREATE_COMPUTER)
    public @ResponseBody ComputerDTO createComputer(@RequestBody ComputerDTO computerDto) {
        LOGGER.info("Computer DTO : " + computerDto);
        return computerService.create(computerDto);
    }
}
