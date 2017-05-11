package com.excilys.cdb.validator;

public final class StringValidator {
    public static boolean checkNoSpecialsChars(String string) {
        return string != null && string.matches("[\\w\\+\\-\\ ]*");
    }

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

    public static boolean checkIsDecade(String string) {
        return string != null && string.matches("[\\d]+0");
    }
}
