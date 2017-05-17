package com.excilys.cdb.validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.ControllerException;
import com.excilys.cdb.model.Computer;

public final class ComputerValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerValidator.class);

    /**
     * Checks if the name is correct.
     *
     * @param name name to check
     * @return true if correct name
     */
    public static boolean checkName(String name) {
        String regex = "[\\d\\w\\+\\-\\ \\.\\']+";
        LOGGER.info("Check name : " + name);
        LOGGER.debug("Regex : " + regex);
        return name != null && name.matches(regex);
    }

    /**
     * Checks if the date is correct.
     *
     * @param date date to check
     * @return true if correct date
     */
    public static boolean checkDate(String date) {
        String regex = "\\d{4}\\-\\d{2}\\-\\d{2}";
        LOGGER.info("Check date : " + date);
        LOGGER.debug("Regex : " + regex);
        return date != null && date.matches(regex);
    }

    /**
     * Checks if the identifier is correct.
     *
     * @param id identifier to check
     * @return true if correct identifier
     */
    public static boolean checkId(String id) {
        String regex = "[1-9][\\d]*";
        LOGGER.info("Check id : " + id);
        LOGGER.debug("Regex : " + regex);
        return id != null && id.matches(regex);
    }

    /**
     * Returns a valid computer to insert.
     *
     * @param parameters request parameters
     * @return valid company
     * @throws ServletException if the computer is not validate
     */
    public static Computer getValidComputer(Map<String, String[]> parameters)
            throws ControllerException {
        LOGGER.info("Get valid computer with parameters : " + parameters.entrySet());
        String paramValue;
        Computer computer = new Computer.Builder().build();
        LocalDate i = null;
        LocalDate d = null;
        String message;

        if (parameters.containsKey("computerName")) {
            paramValue = parameters.get("computerName")[0];
            if (checkName(paramValue)) {
                computer.setName(paramValue);
            } else {
                message = "Sorry, the computer name is not valid : " + paramValue;
                LOGGER.error(message);
                throw new ControllerException(message);
            }
        } else {
            message = "Sorry, the computer is not valid.";
            LOGGER.error(message);
            throw new ControllerException(message);
        }

        if (parameters.containsKey("introduced")) {
            paramValue = parameters.get("introduced")[0];
            if (paramValue != null && !paramValue.isEmpty()) {
                if (checkDate(paramValue)) {
                    i = LocalDate.parse(paramValue);
                } else {
                    message = "Sorry, the date of introduction is not valid : " + paramValue;
                    LOGGER.error(message);
                    throw new ControllerException(message);
                }
            }
        } else {
            throw new ControllerException("Sorry, the computer is not valid.");
        }

        if (parameters.containsKey("discontinued")) {
            paramValue = parameters.get("discontinued")[0];
            if (paramValue != null && !paramValue.isEmpty()) {
                if (checkDate(paramValue)) {
                    d = LocalDate.parse(paramValue);
                } else {
                    message = "Sorry, the date of discontinue is not valid : " + paramValue;
                    LOGGER.error(message);
                    throw new ControllerException(message);
                }
            }
        } else {
            message = "Sorry, the computer is not valid.";
            LOGGER.error(message);
            throw new ControllerException(message);
        }

        if (i != null && d != null) {
            if (i.isBefore(d)) {
                computer.setIntroduced(i);
                computer.setDiscontinued(d);
            } else {
                message = "Sorry, there is a problem with dates : " + i + " " + d;
                LOGGER.error(message);
                throw new ControllerException(message);
            }
        }

        return computer;
    }

    /**
     * Returns a valid list of identifiers.
     *
     * @param ids list of identifiers to check
     * @return list of identifiers
     * @throws ServletException if the selection is not validate
     */
    public static List<Long> getValidIdList(String ids) throws ControllerException {
        LOGGER.info("Get valid list of id : " + ids);
        List<Long> idsList = new ArrayList<>();
        String[] idsTab = ids.split(",");
        for (String id : idsTab) {
            if (checkId(id)) {
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
}
