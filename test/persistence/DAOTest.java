package persistence;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DAOTest {
	private List<DAO> DAOList;
	
	@Before
	public void init() {
		DAOList = new ArrayList<>();
		DAOList.add(new CompanyDAO());
		DAOList.add(new ComputerDAO());
	}
	
	@After
	public void end() {
		DAOList.removeAll(DAOList);
		DAOList = null;
	}
	
	@Test
	public void DAOSameConnection() {
		assertSame(DAOList.get(0).connection, DAOList.get(1).connection);
	}
	
	@Test
	public void ComputerDAOFindNotNull() throws SQLException {
		assertNotNull(DAOList.get(1).findById(1l));
	}
}
