package com.excilys.cdb.validators;

import java.time.format.DateTimeFormatter;

public final class ComputerValidator {
    public static boolean checkName(String name){
        return name.matches("[\\w]");
    }
    
    public static boolean checkDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}
