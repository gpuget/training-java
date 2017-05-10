package com.excilys.cdb.persistence;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.persistence.Connector;

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
