package persistence;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectTest {
	private Connect c;
	
	@Before
	public void init() {
		c = Connect.getInstance();
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
