package com.excilys.cdb.persistences;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum Connector {	
	INSTANCE;
	
	private Connection connection;
	private Properties properties;
	
	private Connector() {
		try {
			properties = new Properties();
			properties.load(new FileInputStream("src/main/resources/local.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void connect(){
		try {
			Class.forName(properties.getProperty("db.driver"));
			connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.username"), properties.getProperty("db.password"));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		try {
			if(connection == null || connection.isClosed()) {
				connect();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public boolean isDisconnected() {
		try {
			return (connection == null || connection.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}