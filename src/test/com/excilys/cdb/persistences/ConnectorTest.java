package com.excilys.cdb.persistences;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

public class ConnectorTest {
	
	@Before
	public void init() {
	
	}
	
	@Test
	public void connectorInstance() {
		Connector c = Connector.INSTANCE;
		Connector c2 = Connector.INSTANCE;
		
		assertSame(c, c2);
	}
	
	@Test
	public void connectionNotNull() {
		Connection c1 = Connector.INSTANCE.getConnection();
		Connection c2 = Connector.INSTANCE.getConnection();
		
		assertNotNull(c1);
		assertSame(c1, c2);
	}
	
	@Test
	public void connectionOpenAndClose() {
		assertFalse(Connector.INSTANCE.isDisconnected());
		Connector.INSTANCE.disconnect();
		assertTrue(Connector.INSTANCE.isDisconnected());
		Connector.INSTANCE.getConnection();
		assertFalse(Connector.INSTANCE.isDisconnected());
	}
}
