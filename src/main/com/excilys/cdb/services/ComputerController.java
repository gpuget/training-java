package com.excilys.cdb.services;

public class ComputerController {
	private static ComputerController instance = null;
	
	private ComputerController() {
		
	}
	
	public static ComputerController getInstance() {
		if(instance == null) instance = new ComputerController();
		return instance;
	}
}