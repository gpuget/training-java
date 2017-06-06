package com.excilys.cdb.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class ComputerTest {
    @Test
    public void equalsTest() {
        Company com1 = new Company.Builder().id(1L).name("Bob Inc.").build();
        Company com2 = new Company.Builder().id(1L).name("Bob").build();
        Computer cpu1 = new Computer.Builder().id(1L).name("Bob").introduced(LocalDate.MIN).discontinued(LocalDate.MAX).manufacturer(com1).build();
        Computer cpu2 = new Computer.Builder().id(1L).name("Bob").introduced(LocalDate.MIN).discontinued(LocalDate.MAX).manufacturer(com1).build();
        Computer cpu3 = new Computer.Builder().id(2L).name("Bob").introduced(LocalDate.MIN).discontinued(LocalDate.MAX).manufacturer(com1).build();
        Computer cpu4 = new Computer.Builder().id(1L).name("Robert").introduced(LocalDate.MIN).discontinued(LocalDate.MAX).manufacturer(com1).build();
        Computer cpu5 = new Computer.Builder().id(1L).name("Bob").discontinued(LocalDate.MAX).manufacturer(com1).build();
        Computer cpu6 = new Computer.Builder().id(1L).name("Bob").introduced(LocalDate.MIN).manufacturer(com1).build();
        Computer cpu7 = new Computer.Builder().id(1L).name("Robert").introduced(LocalDate.MIN).discontinued(LocalDate.MAX).manufacturer(com2).build();

        assertTrue(cpu1.equals(cpu2));
        assertFalse(cpu1.equals(cpu3));
        assertFalse(cpu1.equals(cpu4));
        assertFalse(cpu1.equals(cpu5));
        assertFalse(cpu1.equals(cpu6));
        assertFalse(cpu1.equals(cpu7));
    }
}
