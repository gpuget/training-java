package com.excilys.cdb.validators;

public final class StringValidator {
    public static boolean checkNoSpecialsChars(String string){
        return string.matches("[\\w\\+\\-\\ ]+");
    }
    
    public static boolean checkIsNumber(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean checkIsDecade(String string){
        return string.matches("[\\d]+0");
    }
}
