package com.excilys.cdb.persistences;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.persistences.Connector;

public class ConnectTest {
	private Connector c;
	
	@Before
	public void init() {
		c = Connector.getInstance();
	}
	
	@After
	public void end() {
		c = null;
	}
	
	@Test
	public void connectNotNull() {
		assertNotNull(c);
	}
	
	@Test
	public void connectionNotNull() {
		assertNotNull(c.getConnection());
	}
}
