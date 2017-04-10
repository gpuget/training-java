package persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
	private static Connect instance = null;
	private Connection connection = null;
	
	/*
	private final String DB_TYPE = "jdbc:mysql://";
	private final String DB_HOST = "localhost";
	private final String DB_PORT = "3306";
	private final String DB_NAME = "computer-database-db";
	*/	
	private final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost:3306/computer-database-db";
	
	private final String ADMIN_LOGIN = "admincdb";
	private final String ADMIN_PW = "qwerty1234";
	
	private Connect() {
		super();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		connection.close();
		connection = null;
		instance = null;
	}
	
	public static Connect getInstance() {
		if(instance == null) return new Connect();
		else return instance;
	}
	
	private void connect() throws Exception{
		Class.forName(DB_DRIVER);
		connection = DriverManager.getConnection(DB_URL, ADMIN_LOGIN, ADMIN_PW);
	}
	
	public Connection getConnection(){
		if(connection == null) {
			try {
				connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return connection;
	}
}
