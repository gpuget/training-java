package com.excilys.cdb.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StringValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringValidator.class);

    /**
     * Checks if there are specials characters.
     *
     * @param string string to check
     * @return true if correct string
     */
    public static boolean checkNoSpecialsChars(String string) {
        String regex = "[\\w\\+\\-\\ \\.]*";
        LOGGER.info("Check specials characters : " + string);
        LOGGER.debug("Regex : " + regex);
        return string != null && string.matches(regex);
    }

    /**
     * Checks if it is a correct number.
     *
     * @param string string to check
     * @return true if correct string
     */
    public static boolean checkIsNumber(String string) {
        String regex = "[\\d]+";
        LOGGER.info("Check number : " + string);
        LOGGER.debug("Regex : " + regex);
        return string != null && string.matches(regex);
    }

    /**
     * Checks if it is a decade.
     *
     * @param string string to check
     * @return true if correct string
     */
    public static boolean checkIsDecade(String string) {
        String regex = "[\\d]+0";
        LOGGER.info("Check decade : " + string);
        LOGGER.debug("Regex : " + regex);
        return string != null && string.matches(regex);
    }
}
