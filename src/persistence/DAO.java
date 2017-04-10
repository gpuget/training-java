package persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public abstract class DAO<T> {
	protected static Connection connection = Connect.getInstance().getConnection();
	
	public DAO() {
		
	}
	
	public abstract T create(T obj);
	
	public abstract boolean delete(T obj);
	
	public abstract Collection<T> findAll();
	
	public abstract T findById(long id) throws SQLException;
	
	public abstract T update(T obj);
}