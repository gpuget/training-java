package com.excilys.cdb.persistence;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.cdb.persistence.Connector;

public class ConnectorTest {
	
	@Test
	public void singleInstance() {
		Connector c = Connector.INSTANCE;
		Connector c2 = Connector.INSTANCE;
		
		assertSame(c, c2);
	}
}
