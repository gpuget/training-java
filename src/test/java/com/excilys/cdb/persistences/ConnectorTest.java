package com.excilys.cdb.persistences;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectorTest {
	
	@Before
	public void init() {
	
	}
	
	@After
	public void end() {

	}
	
	@Test
	public void singleInstance() {
		Connector c = Connector.INSTANCE;
		Connector c2 = Connector.INSTANCE;
		
		assertSame(c, c2);
	}
}
