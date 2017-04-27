package com.excilys.cdb.servlets;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ServletTest {   
    @Before
    public void init(){
        EditComputer es = new EditComputer();
    }

    @Test
    public void update() {
        long start = System.currentTimeMillis();
        es.doPost();
    }
}

