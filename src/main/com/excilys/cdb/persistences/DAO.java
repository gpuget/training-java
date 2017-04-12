package com.excilys.cdb.persistences;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO<T> {
	protected static Connection connection = Connect.getInstance().getConnection();
	
	public DAO() {
		
	}
	
	public abstract T create(T obj);
	
	public abstract boolean delete(T obj);
	
	public abstract List<T> findAll() throws SQLException;
	
	public abstract T findById(long id) throws SQLException;
	
	public abstract T update(T obj);
}