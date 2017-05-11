package com.excilys.cdb.validator;

public final class StringValidator {
    /**
     * Checks if there are specials characters.
     *
     * @param string string to check
     * @return true if correct string
     */
    public static boolean checkNoSpecialsChars(String string) {
        return string != null && string.matches("[\\w\\+\\-\\ ]*");
    }

    /**
     * Checks if it is a correct number.
     *
     * @param string string to check
     * @return true if correct string
     */
    public static boolean checkIsNumber(String string) {
        try {
            if (string != null) {
                Integer.parseInt(string);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if it is a decade.
     *
     * @param string string to check
     * @return true if correct string
     */
    public static boolean checkIsDecade(String string) {
        return string != null && string.matches("[\\d]+0");
    }
}
