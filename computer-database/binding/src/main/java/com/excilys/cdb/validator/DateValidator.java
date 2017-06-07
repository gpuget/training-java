package com.excilys.cdb.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateValidator implements ConstraintValidator<Date, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateValidator.class);

    private int min;
    private int max;

    @Override
    public void initialize(Date date) {
        this.min = date.min();
        this.max = date.max();
    }

    @Override
    public boolean isValid(String dateString, ConstraintValidatorContext context) {
        String message = "Not valid date.";
        String regex = "\\d{4}\\-\\d{2}\\-\\d{2}";
        LOGGER.info("Check date : " + dateString);
        LOGGER.debug("Regex : " + regex);


        if (StringUtils.isNotBlank(dateString)) {

            if (dateString.matches(regex)) {
                LocalDate date;
                try {
                    date = LocalDate.parse(dateString,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception e) {
                    LOGGER.error(message + " LocalDate parsing");
                    return false;
                }
                LOGGER.debug("LocalDate cast : " + date);
                int year = date.getYear();
                if (year < min || year > max) {
                    LOGGER.warn(message);
                    return false;

                }

            } else {
                LOGGER.warn(message);
                return false;
            }
        }

        LOGGER.debug("Valid date.");
        return true;
    }
}
