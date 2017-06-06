package com.excilys.cdb.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DateValidatorTest {
    public DateValidator dv;
    
    @Before
    public void inti() {
        dv = new DateValidator();
    }
    
    @Test
    public void validDateNull() {
        String dateString = null;
        
        assertTrue(dv.isValid(dateString, null));
    }
    
    @Test
    public void invalidDate() {
        String dateString1 = "zeeraa";
        String dateString2 = "2323823";
        String dateString3 = "9999-99-99";
        
        assertFalse(dv.isValid(dateString1, null));
        assertFalse(dv.isValid(dateString2, null));
        assertFalse(dv.isValid(dateString3, null));
    }
    
    @Test
    public void limitDate() {
        String dateString1 = "1960-01-01";
        String dateString2 = "2060-01-01";
        
        assertFalse(dv.isValid(dateString1, null));
        assertFalse(dv.isValid(dateString2, null));
    }
}
