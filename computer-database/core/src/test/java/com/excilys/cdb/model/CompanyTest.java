package com.excilys.cdb.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CompanyTest {
    @Test
    public void equalsTest() {
        Company com1 = new Company.Builder().id(1L).name("Bob Inc.").build();
        Company com2 = new Company.Builder().id(1L).name("Bob Inc.").build();
        Company com3 = new Company.Builder().id(1L).name("Bob").build();
        Company com4 = new Company.Builder().id(2L).name("Bob Inc.").build();

        assertTrue(com1.equals(com2));
        assertFalse(com1.equals(com3));
        assertFalse(com1.equals(com4));
    }
}
